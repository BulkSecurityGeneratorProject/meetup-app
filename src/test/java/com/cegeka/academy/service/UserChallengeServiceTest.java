package com.cegeka.academy.service;

import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.*;
import com.cegeka.academy.domain.enums.InvitationStatus;
import com.cegeka.academy.domain.enums.UserChallengeStatus;
import com.cegeka.academy.repository.*;
import com.cegeka.academy.service.dto.UserChallengeDTO;
import com.cegeka.academy.service.mapper.UserChallengeMapper;
import com.cegeka.academy.service.userChallenge.UserChallengeService;
import com.cegeka.academy.web.rest.errors.InvalidUserChallengeStatusException;
import com.cegeka.academy.web.rest.errors.NotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class UserChallengeServiceTest  {

    @Autowired
    private UserChallengeRepository userChallengeRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeCategoryRepository challengeCategoryRepository;

    @Autowired
    private UserChallengeService userChallengeService;

    private Invitation invitation;
    private User user;
    private Challenge challenge;
    private ChallengeCategory challengeCategory;
    private UserChallenge userChallenge;
    private User usedUser;

    @BeforeEach
    public void init() {

        user = new User();
        user.setLogin("ana");
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("ana@gmail.com");
        user.setFirstName("ana");
        user.setLastName("doe");
        user.setImageUrl("http://placehold.it/50x50");
        user.setLangKey("mmmm");
        usedUser = userRepository.save(user);

        invitation = new Invitation();
        invitation.setDescription("invitationDescription");
        invitation.setStatus("pending");
        invitation.setUser(usedUser);
        invitation.setEvent(null);
        invitationRepository.save(invitation);

        Date startDate = new Date();
        Date endDate = new Date();

        challengeCategory = new ChallengeCategory();
        challengeCategory.setName("challengeCategory");
        challengeCategory.setDescription("challengeCategoryDescription");
        challengeCategoryRepository.save(challengeCategory);

        challenge = new Challenge();
        challenge.setCreator(userRepository.findAll().get(0));
        challenge.setPoints(5.22);
        challenge.setStartDate(startDate);
        challenge.setEndDate(endDate);
        challenge.setStatus("new");
        challenge.setDescription("description");
        challenge.setChallengeCategory(challengeCategoryRepository.findAll().get(0));
        challengeRepository.save(challenge);


        userChallenge = new UserChallenge();
        userChallenge.setUser(usedUser);
        userChallenge.setInvitation(invitationRepository.findAll().get(0));
        userChallenge.setChallenge(challengeRepository.findAll().get(0));
        userChallenge.setStatus("status");
        userChallenge.setPoints(2.22);
        userChallenge.setStartTime(new Date());
        userChallenge.setEndTime(new Date());
        userChallengeRepository.save(userChallenge);

    }

    @AfterEach
    public void destroy(){

        if(invitation != null){
            invitationRepository.delete(invitation);
        }

        if(user != null){
            userRepository.delete(user);
        }

        if(challengeCategory != null){
            challengeCategoryRepository.delete(challengeCategory);
        }

        if(challenge != null){
            challengeRepository.delete(challenge);
        }

        if(userChallenge != null){
            userChallengeRepository.delete(userChallenge);
        }
    }

    @Test
    public void testGetChallengesByUserId(){

       List<UserChallengeDTO> results = userChallengeService.getUserChallengesByUserId(usedUser.getId());
        assertThat(UserChallengeMapper.convertUserChallengeDTOToUserChallenge(results.get(0))).isEqualTo(userChallenge);

    }

    @Test
    public void testGetChallengesByUserIdWithNoResult(){

        List<UserChallengeDTO> results = userChallengeService.getUserChallengesByUserId(20l);
        assertThat(results.size()).isEqualTo(0);

    }

    @Test
    public void testUpdateUserChallengeStatusIsWorking() throws NotFoundException, InvalidUserChallengeStatusException {

        UserChallenge existingUserChallenge = userChallengeRepository.findAll().get(0);

        userChallengeService.updateUserChallengeStatus(existingUserChallenge.getId(), UserChallengeStatus.ACCEPTED.toString());

        assertThat(userChallengeRepository.findAll().get(0).getId()).isEqualTo(existingUserChallenge.getId());
        assertThat(userChallengeRepository.findAll().get(0).getStatus()).isEqualTo(UserChallengeStatus.ACCEPTED.toString());

    }

    @Test
    public void testUpdateUserChallengeStatusWithInvalidId() {

        Assertions.assertThrows(NotFoundException.class, () -> {

            userChallengeService.updateUserChallengeStatus(100L, UserChallengeStatus.ACCEPTED.toString());

        });

    }

    @Test
    public void testUpdateUserChallengeStatusWithInvalidStatus() {

        Assertions.assertThrows(InvalidUserChallengeStatusException.class, () -> {

            userChallengeService.updateUserChallengeStatus(userChallengeRepository.findAll().get(0).getId(), "status");

        });

    }

    @Test
    public void testUpdateUserChallengeInvitationStatusIsWorking() throws NotFoundException, InvalidUserChallengeStatusException {

        UserChallenge existingUserChallenge = userChallengeRepository.findAll().get(0);

        userChallengeService.updateUserChallengeInvitationStatus(existingUserChallenge.getId(), InvitationStatus.CANCELED.toString());

        assertThat(userChallengeRepository.findAll().get(0).getId()).isEqualTo(existingUserChallenge.getId());
        assertThat(userChallengeRepository.findAll().get(0).getInvitation().getStatus()).isEqualTo(InvitationStatus.CANCELED.toString());

    }

    @Test
    public void testUpdateUserChallengeInvitationStatusWithInvalidId() {

        Assertions.assertThrows(NotFoundException.class, () -> {

            userChallengeService.updateUserChallengeInvitationStatus(100L, InvitationStatus.CANCELED.toString());

        });

    }

    @Test
    public void testUpdateUserChallengeInvitationStatusWithInvalidStatus() {

        Assertions.assertThrows(InvalidUserChallengeStatusException.class, () -> {

            userChallengeService.updateUserChallengeInvitationStatus(userChallengeRepository.findAll().get(0).getId(), "status");
        });

    }

}
