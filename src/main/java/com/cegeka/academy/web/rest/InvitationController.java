package com.cegeka.academy.web.rest;

import com.cegeka.academy.domain.Invitation;
import com.cegeka.academy.service.invitation.InvitationService;
import com.cegeka.academy.service.invitation.ValidationAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("invitation")
public class InvitationController {

    private final InvitationService invitationService;

    private final ValidationAccessService validationAccessService;

    @Autowired
    public InvitationController(InvitationService invitationService, ValidationAccessService validationAccessService) {
        this.invitationService = invitationService;
        this.validationAccessService = validationAccessService;
    }

    @GetMapping("/all")
    public String getAllInvitations(){
        return invitationService.getAllInvitations();
    }

    @PostMapping("/post")
    public String saveInvitation(@RequestBody Invitation newInvitation){
        return invitationService.saveInvitation(newInvitation);
    }

    @PutMapping("/update")
    public String replaceInvitation(@RequestBody Invitation newInvitation){
        validationAccessService.verifyUserAccessForInvitationEntity(newInvitation.getId());
        return invitationService.updateInvitation(newInvitation);
    }

    @DeleteMapping("/invitation/{id}")
    void deleteInvitation(@PathVariable Long id) {
        validationAccessService.verifyUserAccessForInvitationEntity(id);
        invitationService.deleteInvitationById(id);
    }

}
