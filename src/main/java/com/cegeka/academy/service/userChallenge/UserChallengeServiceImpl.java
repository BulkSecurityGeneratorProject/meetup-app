package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.ChallengeAnswer;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.domain.enums.UserChallengeStatus;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import com.cegeka.academy.web.rest.errors.InvalidInvitationStatusException;
import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import com.cegeka.academy.web.rest.errors.WrongOwnerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserChallengeServiceImpl implements UserChallengeService {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

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
    public void updateUserChallengeAnswer(UserChallenge userChallenge, ChallengeAnswer challengeAnswer) {
        userChallenge.setChallengeAnswer(challengeAnswer);

        userChallengeRepository.save(userChallenge);
    }
}
