package com.cegeka.academy.service.userChallenge;

import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.domain.enums.UserChallengeStatus;
import com.cegeka.academy.repository.UserChallengeRepository;
import com.cegeka.academy.service.dto.*;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        if(status.equalsIgnoreCase(UserChallengeStatus.ACCEPTED.toString()) || status.equalsIgnoreCase(UserChallengeStatus.CANCELED.toString())){

            userChallenge.setStatus(status);

            userChallengeRepository.save(userChallenge);

        } else {

            throw new InvalidUserChallengeStatusException().setMessage("Status is invalid");
        }

    }

    @Override
    public void updateUserChallengeInvitationStatus(Long userChallengeId, String status) throws NotFoundException, InvalidUserChallengeStatusException {

        UserChallenge userChallenge = userChallengeRepository.findById(userChallengeId)
                .orElseThrow(() -> new NotFoundException().setMessage("User challenge does not exists."));

        if(status.equalsIgnoreCase(InvitationStatus.ACCEPTED.toString()) || status.equalsIgnoreCase(InvitationStatus.CANCELED.toString()) || status.equalsIgnoreCase(InvitationStatus.REJECTED.toString())){

            userChallenge.getInvitation().setStatus(status);

            userChallengeRepository.save(userChallenge);

        } else {

            throw new InvalidUserChallengeStatusException().setMessage("Status is invalid");
        }

    }
}
