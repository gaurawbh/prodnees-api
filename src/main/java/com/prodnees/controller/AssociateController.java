package com.prodnees.controller;

import com.prodnees.action.rel.AssociateInvitationAction;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.auth.service.UserAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.enums.InvitationAction;
import com.prodnees.domain.rels.AssociateInvitation;
import com.prodnees.domain.rels.Associates;
import com.prodnees.dto.AssociateInvitationActionDto;
import com.prodnees.dto.AssociateInvitationDto;
import com.prodnees.dto.user.SignupDto;
import com.prodnees.model.user.AssociateModel;
import com.prodnees.model.user.UserModel;
import com.prodnees.service.rels.AssociatesService;
import com.prodnees.web.exception.NeesNotFoundException;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

import static com.prodnees.config.constants.APIErrors.OBJECT_NOT_FOUND;
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
    private final UserAction userAction;
    private final AssociateInvitationAction associateInvitationAction;

    public AssociateController(AssociatesService associatesService,
                               UserAction userAction,
                               AssociateInvitationAction associateInvitationAction) {
        this.associatesService = associatesService;
        this.userAction = userAction;
        this.associateInvitationAction = associateInvitationAction;
    }

    /**
     * @param id
     * @param email
     * @return
     */
    @GetMapping("/associates")
    public ResponseEntity<?> getAssociates(@RequestParam Optional<Integer> id,
                                           @RequestParam Optional<Integer> email) {
        int adminId = RequestContext.getUserId();
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
     * @return
     */
    @GetMapping("/associates/search")
    public ResponseEntity<?> searchAssociate(@RequestParam String email) {
        Assert.isTrue(userAction.existsByEmail(email), APIErrors.USER_NOT_FOUND.getMessage());
        int adminId = RequestContext.getUserId();
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
    @PostMapping("/associate-invitation")
    public ResponseEntity<?> inviteAssociate(@Validated @RequestBody AssociateInvitationDto dto) {
        String invitorEmail = RequestContext.getUsername();
        int invitorId = RequestContext.getUserId();
        Optional<AssociateInvitation> associateInvitationOpt = associateInvitationAction.findByInvitorEmailAndInviteeEmail(invitorEmail, dto.getInviteeEmail());
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        associateInvitationOpt.ifPresentOrElse(associateInvitation -> {
            Assert.isTrue(!associateInvitation.getAction().equals(InvitationAction.ACCEPT),
                    String.format("invitation has already been sent to %s", dto.getInviteeEmail()));
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
                SignupDto registrationDto = new SignupDto()
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

    /**
     * @param servletRequest
     * @return List of {@link AssociateInvitation}
     * <p>the invitor has invited </p>
     * <p>the invitee been invited</p>
     */
    @GetMapping("/associates-invitations/{path}")
    public ResponseEntity<?> getInvitationListByyInvitor(@PathVariable String path,
                                                         HttpServletRequest servletRequest) {
        int userId = RequestContext.getUserId();
        switch (path) {
            case "invites":
                return LocalResponse.configure(associateInvitationAction.getAllByInvitorId(userId));
            case "requests":
                return LocalResponse.configure(associateInvitationAction.getAllByInviteeId(userId));
            default:
                throw new NeesNotFoundException(String.format("no handler found for %s", servletRequest.getRequestURI()), 99);
        }
    }

    /**
     * Update the  {@link AssociateInvitation} with the dto field values.
     * <p>Send email that the invitation has been actioned</p>
     *
     * @param dto
     * @return
     */
    @PutMapping("/associate-invitation/action")
    public ResponseEntity<?> actionInvitationRequest(@Validated @RequestBody AssociateInvitationActionDto dto) {
        String inviteeEmail = RequestContext.getUsername();
        Optional<AssociateInvitation> associateInvitationOpt = associateInvitationAction.findByInvitorEmailAndInviteeEmail(dto.getInvitorEmail(), inviteeEmail);
        Assert.isTrue(associateInvitationOpt.isPresent(), OBJECT_NOT_FOUND.getMessage());
        associateInvitationOpt.get().setAccepted(dto.isAccept())
                .setAction(dto.isAccept() ? InvitationAction.ACCEPT : InvitationAction.DENY);

        if (associateInvitationOpt.get().getAction().equals(InvitationAction.NONE)) {
            return configure("This request is already actioned.");
        }
        boolean isSuccess = associateInvitationAction.actionInvitationRequest(dto.getInvitorEmail(), dto.isAccept());
        return configure(isSuccess
                ? "invitation sent successfully"
                : "some unknown error has occurred when trying to email the collaborator, we are looking into it");
    }

    /**
     * A User can delete an  {@link AssociateInvitation} if the User is the Invitor
     *
     * @param inviteeEmail
     * @return
     */
    @DeleteMapping("/associate-invitation")
    public ResponseEntity<?> delete(@RequestParam String inviteeEmail) {
        String invitorEmail = RequestContext.getUsername();
        Optional<AssociateInvitation> associateInvitationOpt = associateInvitationAction.findByInvitorEmailAndInviteeEmail(invitorEmail, inviteeEmail);
        Assert.isTrue(associateInvitationOpt.isPresent(), OBJECT_NOT_FOUND.getMessage());
        boolean isSuccess = associateInvitationAction.deleteByInvitorEmailAndInviteeEmail(invitorEmail, inviteeEmail);
        return configure(isSuccess
                ? "record deleted successfully"
                : "there was an error deleting the record. we are looking into it");
    }
}
