package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Challenge;
import com.cegeka.academy.service.challenge.ChallengeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/challenge")
public class ChallengeController {

    @Autowired
    ChallengeServiceImp challengeService;

    @PostMapping("/create")
    public String saveChallenge(@RequestBody Challenge challenge) {
        return challengeService.saveChallenge(challenge);
    }
}
