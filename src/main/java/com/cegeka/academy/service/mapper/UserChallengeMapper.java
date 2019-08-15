package com.cegeka.academy.service.mapper;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.domain.UserChallenge;
import com.cegeka.academy.service.dto.*;

public class UserChallengeMapper {


    public static UserChallenge convertUserChallengeDTOToUserChallenge(UserChallengeDTO userChallengeDTO){

        if(userChallengeDTO != null){

            UserChallenge userChallenge = new UserChallenge();
            userChallenge.setId(userChallengeDTO.getId());
            userChallenge.setUser(new UserMapper().userDTOToUser(userChallengeDTO.getUser()));
            userChallenge.setInvitation(convertInvitationChallengeDTOTOInvitation(userChallengeDTO.getInvitation()));
            userChallenge.setChallenge(convertChallengeDTOToChallenge(userChallengeDTO.getChallenge()));
            userChallenge.setStatus(userChallengeDTO.getStatus());
            userChallenge.setPoints(userChallengeDTO.getPoints());
            userChallenge.setStartTime(userChallengeDTO.getStartTime());
            userChallenge.setEndTime(userChallengeDTO.getEndTime());

            return userChallenge;
        }

        return null;
    }

    public static UserChallengeDTO convertUserChallengeToUserChallengeDTO(UserChallenge userChallenge){

        if(userChallenge != null){

            UserChallengeDTO userChallengeDTO = new UserChallengeDTO();
            userChallengeDTO.setId(userChallenge.getId());
            userChallengeDTO.setUser(new UserMapper().userToUserDTO(userChallenge.getUser()));
            userChallengeDTO.setInvitation(convertInvitationToInvitationChallengeDTO(userChallenge.getInvitation()));
            userChallengeDTO.setChallenge(convertChallengeToChallengeDTO(userChallenge.getChallenge()));
            userChallengeDTO.setStatus(userChallengeDTO.getStatus());
            userChallengeDTO.setPoints(userChallengeDTO.getPoints());
            userChallengeDTO.setStartTime(userChallengeDTO.getStartTime());
            userChallengeDTO.setEndTime(userChallengeDTO.getEndTime());

            return userChallengeDTO;
        }

        return null;
    }

    public static ChallengeDTO convertChallengeToChallengeDTO(Challenge challenge){

        ChallengeDTO challengeDTO = new ChallengeDTO();
        challengeDTO.setId(challenge.getId());
        challengeDTO.setCreator(new UserMapper().userToUserDTO(challenge.getCreator()));
        challengeDTO.setStartDate(challenge.getStartDate());
        challengeDTO.setEndDate(challenge.getEndDate());
        challengeDTO.setPoints(challenge.getPoints());

        return challengeDTO;
    }

    public static Challenge convertChallengeDTOToChallenge(ChallengeDTO challengeDTO){

        Challenge challenge = new Challenge();
        challenge.setId(challenge.getId());
        challenge.setCreator(new UserMapper().userDTOToUser(challengeDTO.getCreator()));
        challenge.setStartDate(challengeDTO.getStartDate());
        challenge.setEndDate(challengeDTO.getEndDate());
        challenge.setPoints(challengeDTO.getPoints());

        return challenge;
    }

    public static InvitationChallengeDTO convertInvitationToInvitationChallengeDTO(Invitation invitation){

        InvitationChallengeDTO invitationChallengeDTO = new InvitationChallengeDTO();
        invitationChallengeDTO.setUser(new UserMapper().userToUserDTO(invitation.getUser()));
        invitationChallengeDTO.setId(invitation.getId());
        invitationChallengeDTO.setStatus(invitation.getStatus());

        return invitationChallengeDTO;
    }

    public static Invitation convertInvitationChallengeDTOTOInvitation(InvitationChallengeDTO invitationChallengeDTO){

        Invitation invitation = new Invitation();
        invitation.setUser(new UserMapper().userDTOToUser(invitationChallengeDTO.getUser()));
        invitation.setId(invitationChallengeDTO.getId());
        invitation.setStatus(invitationChallengeDTO.getStatus());

        return invitation;
    }

}
