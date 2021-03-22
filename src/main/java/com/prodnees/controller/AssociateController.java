package com.prodnees.controller;

import com.prodnees.action.UserAction;
import com.prodnees.action.rel.AssociateInvitationAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.rels.Associates;
import com.prodnees.filter.UserValidator;
import com.prodnees.model.AssociateModel;
import com.prodnees.service.rels.AssociatesService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure/")
@CrossOrigin
@Transactional
public class AssociateController {
    private final AssociatesService associatesService;
    private final UserValidator userValidator;
    private final UserAction userAction;
    private final AssociateInvitationAction associateInvitationAction;
    public AssociateController(AssociatesService associatesService,
                               UserValidator userValidator,
                               UserAction userAction,
                               AssociateInvitationAction associateInvitationAction) {
        this.associatesService = associatesService;
        this.userValidator = userValidator;
        this.userAction = userAction;
        this.associateInvitationAction = associateInvitationAction;
    }


    /**
     * @param id
     * @param email
     * @param servletRequest
     * @return
     */
    @GetMapping("/associates")
    public ResponseEntity<?> getAssociates(@RequestParam Optional<Integer> id,
                                           @RequestParam Optional<Integer> email,
                                           HttpServletRequest servletRequest) {
        int adminId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> modelAtomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            Assert.isTrue(associatesService.findByAdminIdAndAssociateId(adminId, integer).isPresent(), APIErrors.USER_NOT_FOUND.getMessage());
            modelAtomicReference.set(userAction.getAssociateById(integer));
        }, () -> modelAtomicReference.set(userAction.getAllAssociates(adminId)));

        return configure(modelAtomicReference.get());
    }


    /**
     * If an associate exists by the provided email, it returns AssociateModel with address and phoneNumber
     * <p>if an associate does not exist by email, address and phoneNumber fields are hidden, isAssoicate is set to false</p>
     *
     * @param email
     * @param servletRequest
     * @return
     */
    @GetMapping("/associates/search")
    public ResponseEntity<?> searchAssociate(@RequestParam String email,
                                             HttpServletRequest servletRequest) {
        Assert.isTrue(userAction.existsByEmail(email), APIErrors.USER_NOT_FOUND.getMessage());
        int adminId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> modelAtomicReference = new AtomicReference<>();
        Optional<Associates> associatesOptional = associatesService.findByAdminIdAndAssociateEmail(adminId, email);
        associatesOptional.ifPresentOrElse(
                associates -> modelAtomicReference.set(userAction.getAssociateById(associates.getAssociateId()).setAssociate(true)),
                () -> {
                    AssociateModel associateModel = userAction.getAssociateByEmail(email)
                            .setAddress("hidden")
                            .setPhoneNumber("hidden")
                            .setAssociate(false);
                    modelAtomicReference.set(associateModel);
                });
        return configure(modelAtomicReference.get());
    }

    /**
     * <p>Check if an invitation already exists which is not actioned</p>
     * <i>if already actioned and is denied, send the invitation again</i>
     * <i>if not yet actioned, set message, "invitation has already been sent"</i>
     * <p>If an invitation does not already exists, regardless of if the invitee is a user of this application</p>
     * <i>Send the invitation</i>
     * <p>Include a link to signup in the invitation email</p>
     *
     * @param email
     * @return
     */
    @GetMapping("/associates/invite")
    public ResponseEntity<?> inviteAssociate(@RequestParam String email) {


        return configure();

    }
}
