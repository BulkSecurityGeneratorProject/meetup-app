package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.*;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.domain.enums.UserChallengeStatus;
import com.cegeka.academy.repository.ChallengeRepository;
import com.cegeka.academy.repository.InvitationRepository;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.repository.UserRepository;
import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import com.cegeka.academy.web.rest.errors.InvalidInvitationStatusException;
import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import com.cegeka.academy.web.rest.errors.WrongOwnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserChallengeServiceImpl implements UserChallengeService {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserChallengeDTO> getUserChallengesByUserId(Long userId) {

        List<UserChallenge> userChallengeList = userChallengeRepository.findAllByUserId(userId);

        return userChallengeList.stream().map(userChallenge -> UserChallengeMapper.convertUserChallengeToUserChallengeDTO(userChallenge)).collect(Collectors.toList());

    }

    @Override
    public void updateUserChallengeStatus(Long userChallengeId, String status) throws NotFoundException, InvalidUserChallengeStatusException {

        UserChallenge userChallenge = userChallengeRepository.findById(userChallengeId)
                .orElseThrow(() -> new NotFoundException().setMessage("User challenge does not exists."));

        userChallenge.setStatus(UserChallengeStatus.getUserChallengeStatus(status).toString());

        userChallengeRepository.save(userChallenge);

    }

    @Override
    public void updateUserChallengeInvitationStatus(Long userChallengeId, String status) throws NotFoundException, InvalidInvitationStatusException {

        UserChallenge userChallenge = userChallengeRepository.findById(userChallengeId)
                .orElseThrow(() -> new NotFoundException().setMessage("User challenge does not exists."));

        userChallenge.getInvitation().setStatus(InvitationStatus.getInvitationStatus(status).toString());

        userChallengeRepository.save(userChallenge);

    }

    @Override
    public UserChallenge rateUser(UserChallengeDTO userChallengeDTO, Long ownerId)
            throws WrongOwnerException {

        long userId = userChallengeDTO.getUser().getId();
        long challengeId = userChallengeDTO.getChallenge().getId();
        long invitationId = userChallengeDTO.getInvitation().getId();
        UserChallenge userChallenge = userChallengeRepository
                .findByUserIdAndChallengeIdAndInvitationId(userId, challengeId, invitationId).orElseThrow(NoSuchElementException::new);

        userChallenge.setPoints(userChallengeDTO.getPoints());

        if (userChallenge.getChallenge().getCreator().getId().equals(ownerId)) {
            return userChallengeRepository.save(userChallenge);
        } else {
            throw new WrongOwnerException();
        }
    }

    @Override
    public UserChallenge initUserChallenge(Challenge challenge, Invitation invitation) throws NotFoundException {

        UserChallenge userChallenge = new UserChallenge();

        challengeRepository.findById(challenge.getId()).orElseThrow(
                () -> new NotFoundException().setMessage("Challenge not found"));
        invitationRepository.findById(invitation.getId()).orElseThrow(
                () -> new NotFoundException().setMessage("Invitation not found"));
        User invitedUser = invitation.getUser();
        userRepository.findById(invitedUser.getId()).orElseThrow(
                () -> new NotFoundException().setMessage("User not found"));

        userChallenge.setChallenge(challenge);
        userChallenge.setPoints(0);
        userChallenge.setUser(invitation.getUser());
        userChallenge.setInvitation(invitation);
        userChallenge.setStatus("Not started");
        userChallenge.setChallengeAnswer(null);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());

        return userChallengeRepository.save(userChallenge);
    }

    @Override
    public void updateUserChallengeAnswer(UserChallenge userChallenge, ChallengeAnswer challengeAnswer) {
        userChallenge.setChallengeAnswer(challengeAnswer);

        userChallengeRepository.save(userChallenge);
    }
}
