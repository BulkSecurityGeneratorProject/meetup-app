package com.cegeka.academy.repository;

import com.cegeka.academy.domain.UserChallenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChallengeRepository extends JpaRepository<UserChallenge, Long> {

    List<UserChallenge> findAllByInvitationId(Long challengeId);

    List<UserChallenge> findAllByUserId(Long userId);

    UserChallenge findAllByUserIdAndChallengeId(Long userId, Long challengeId);

    UserChallenge findByUserIdAndChallengeIdAndInvitationId(Long userId, Long challengeId, Long invitationId);

}
