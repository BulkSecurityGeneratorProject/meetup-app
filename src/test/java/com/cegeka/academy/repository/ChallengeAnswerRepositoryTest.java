package com.cegeka.academy.repository;


import com.cegeka.academy.AcademyProjectApp;
import com.cegeka.academy.domain.ChallengeAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = AcademyProjectApp.class)
@Transactional
public class ChallengeAnswerRepositoryTest {

    @Autowired
    private ChallengeAnswerRepository challengeAnswerRepository;

    private ChallengeAnswer challengeAnswer;

    @BeforeEach
    public void setUp(){

        challengeAnswer = new ChallengeAnswer();
        challengeAnswer.setImagePath("imagePath");
        challengeAnswer.setVideoAt("videoAt");
        challengeAnswer.setAnswer("answer");

    }

    @BeforeEach
    public void destroy(){

        challengeAnswerRepository.deleteAll();
    }

    @Test
    public void testSaveChallengeAnswer(){

        assertThat(challengeAnswerRepository.save(challengeAnswer)).isEqualTo(challengeAnswerRepository.findAll().get(0));

    }

    @Test
    public void testFindChallengeAnswerByImagePath(){

        challengeAnswerRepository.save(challengeAnswer);
        assertThat(challengeAnswerRepository.findByImagePath("imagePath")).isEqualTo(challengeAnswerRepository.findAll().get(0));

    }

    @Test
    public void testFindChallengeAnswerByImagePathWithNoResult(){

        challengeAnswerRepository.save(challengeAnswer);
        assertThat(challengeAnswerRepository.findByImagePath("imagePath1")).isEqualTo(null);

    }

    @Test
    public void testFindChallengeAnswerByVideoAt(){

        challengeAnswerRepository.save(challengeAnswer);
        assertThat(challengeAnswerRepository.findByVideoAt("videoAt")).isEqualTo(challengeAnswerRepository.findAll().get(0));

    }

    @Test
    public void testFindChallengeAnswerByVideoAtWithNoResult(){

        challengeAnswerRepository.save(challengeAnswer);
        assertThat(challengeAnswerRepository.findByImagePath("videoAt1")).isEqualTo(null);

    }


}