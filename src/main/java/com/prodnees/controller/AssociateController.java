package com.prodnees.controller;

import com.prodnees.action.UserAction;
import com.prodnees.action.rel.AssociateInvitationAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.InvitationAction;
import com.prodnees.domain.User;
import com.prodnees.domain.rels.AssociateInvitation;
import com.prodnees.domain.rels.Associates;
import com.prodnees.dto.AssociateInvitationDto;
import com.prodnees.dto.UserRegistrationDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.model.AssociateModel;
import com.prodnees.model.UserModel;
import com.prodnees.service.rels.AssociatesService;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import static com.prodnees.web.response.LocalResponse.configure;

/**
 * Controller to operate on {@link AssociateInvitation} object.
 */
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
     * @param dto {@link AssociateInvitationDto}
     * @returns ResponseEntity
     */
    @PostMapping("/associates/invite")
    public ResponseEntity<?> inviteAssociate(@Validated @RequestBody AssociateInvitationDto dto,
                                             HttpServletRequest servletRequest) {
        String invitorEmail = userValidator.extractUserEmail(servletRequest);
        int invitorId = userValidator.extractUserId(servletRequest);
        Optional<AssociateInvitation> associateInvitationOpt = associateInvitationAction.findByInvitorEmailAndInviteeEmail(invitorEmail, dto.getInviteeEmail());
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        associateInvitationOpt.ifPresentOrElse(associateInvitation -> {
            Assert.isTrue(!associateInvitation.getAction().equals(InvitationAction.ACCEPT),
                    String.format("invitation has already been sent to %", dto.getInviteeEmail()));
            associateInvitation.setAccepted(false)
                    .setInvitorComment(dto.getInvitorComment())
                    .setInviteeComment(null)
                    .setAction(InvitationAction.NONE)
                    .setInvitationDate(LocalDate.now());
            associateInvitationAction.save(associateInvitation);
            atomicBoolean.set(associateInvitationAction.sendInvitationIfUser(invitorEmail, dto.getInvitorComment(), dto.getInviteeEmail(), true));
        }, () -> {
            Optional<User> userOptional = Optional.ofNullable(userAction.getByEmail(dto.getInviteeEmail()));
            userOptional.ifPresentOrElse(invitee -> {
                createNewAssociateInvitation(invitee, invitorId, invitorEmail, dto.getInvitorComment());
                atomicBoolean.set(associateInvitationAction.sendInvitationIfUser(invitorEmail, dto.getInvitorComment(), invitee.getEmail(), true));
            }, () -> {
                UserRegistrationDto registrationDto = new UserRegistrationDto()
                        .setEmail(dto.getInviteeEmail())
                        .setFirstName(dto.getInviteeFirstName())
                        .setLastName(dto.getInviteeLastName());
                atomicBoolean.set(associateInvitationAction.sendInvitationIfUser(invitorEmail, dto.getInvitorComment(), registrationDto.getEmail(), false));
                UserModel userModel = userAction.save(registrationDto);
                User invitee = new User()
                        .setId(userModel.getId())
                        .setEmail(userModel.getEmail());
                createNewAssociateInvitation(invitee, invitorId, invitorEmail, dto.getInvitorComment());
            });
        });

        return configure(atomicBoolean.get()
                ? "invitation sent successfully"
                : "some unknown error has occurred when trying to email the collaborator, we are looking into it");

    }

    private void createNewAssociateInvitation(User invitee, int invitorId, String invitorEmail, String invitorComment) {
        AssociateInvitation associateInvitation = new AssociateInvitation()
                .setInviteeId(invitee.getId())
                .setInviteeEmail(invitee.getEmail())
                .setInviteeComment(null)
                .setInvitorId(invitorId)
                .setInvitorEmail(invitorEmail)
                .setInvitorComment(invitorComment)
                .setAccepted(false)
                .setAction(InvitationAction.NONE)
                .setInvitationDate(LocalDate.now());
        associateInvitationAction.save(associateInvitation);
    }

    @GetMapping("/associates/invitations")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
        }, () -> {
        });
        return LocalResponse.configure();
    }

    @PutMapping("/associates/action")
    public ResponseEntity<?> update(@RequestParam boolean accept, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        return configure();
    }
}
