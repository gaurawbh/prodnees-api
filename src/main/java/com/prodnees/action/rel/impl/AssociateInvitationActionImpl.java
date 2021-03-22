package com.prodnees.action.rel.impl;

import com.prodnees.action.rel.AssociateInvitationAction;
import com.prodnees.domain.rels.AssociateInvitation;
import com.prodnees.service.email.EmailPlaceHolders;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.rels.AssociateInvitationService;
import com.prodnees.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AssociateInvitationActionImpl implements AssociateInvitationAction {
    private final LocalEmailService localEmailService;
    private final AssociateInvitationService associateInvitationService;
    Logger localLogger = LoggerFactory.getLogger(this.getClass());

    public AssociateInvitationActionImpl(LocalEmailService localEmailService,
                                         AssociateInvitationService associateInvitationService) {
        this.localEmailService = localEmailService;
        this.associateInvitationService = associateInvitationService;
    }

    @Override
    public AssociateInvitation save(AssociateInvitation associateInvitation) {
        return associateInvitationService.save(associateInvitation);
    }

    @Override
    public boolean existsByInvitorIdAndInviteeIdAndAccepted(int invitorId, int inviteeId, boolean accepted) {
        return associateInvitationService.existsByInvitorIdAndInviteeIdAndAccepted(invitorId, inviteeId, accepted);
    }

    @Override
    public Optional<AssociateInvitation> findByInvitorIdAndInviteeId(int invitorId, int inviteeId) {
        return associateInvitationService.findByInvitorIdAndInviteeId(invitorId, inviteeId);
    }

    @Override
    public Optional<AssociateInvitation> findByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail) {
        return associateInvitationService.findByInvitorEmailAndInviteeEmail(invitorEmail, inviteeEmail);
    }

    @Override
    public List<AssociateInvitation> getAllByInvitorId(int invitorId) {
        return associateInvitationService.getAllByInvitorId(invitorId);
    }

    @Override
    public List<AssociateInvitation> getAllByInviteeId(int inviteeId) {
        return associateInvitationService.getAllByInviteeId(inviteeId);
    }

    @Override
    public boolean sendInvitationIfUser(String invitorEmail, String invitorComment, String inviteeEmail, boolean inviteeIsUser) {
        Map<String, Object> invitationMailMap = new HashMap<>();
        invitationMailMap.put(EmailPlaceHolders.TITLE, "Invitation to collaborate ");
        invitationMailMap.put(EmailPlaceHolders.MESSAGE,
                ValidatorUtil.ifValidOrElse(invitorComment, String.format("You have been invited by %s to collaborate in their Production process.", invitorEmail)));
        invitationMailMap.put(EmailPlaceHolders.PARA_ONE,
                inviteeIsUser
                        ? "Please login to see all your collaboration requests"
                        : String.format("If you did not have an account with us, we have sent you your temporary password for %s", inviteeEmail));

        try {
            localEmailService.sendTemplateEmail(inviteeEmail, "Invitation to collaborate ", invitationMailMap);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            localLogger.error(e.getCause().getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actionInvitationRequest(String invitorEmail, boolean accept) {
        Map<String, Object> invitationActionMailMail = new HashMap<>();
        invitationActionMailMail.put(EmailPlaceHolders.TITLE, "Collaboration invitation has been actioned");
        invitationActionMailMail.put(EmailPlaceHolders.MESSAGE, "Your collaboration invitation has been actioned. Login to see the details");
        try {
            localEmailService.sendTemplateEmail(invitorEmail, "Collaboration invitation has been actioned", invitationActionMailMail);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            localLogger.error(e.getCause().getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail) {
        boolean isSuccess = associateInvitationService.deleteByInvitorEmailAndInviteeEmail(invitorEmail, inviteeEmail);
        if(!isSuccess){
            localLogger.warn("could not delete AssociationInvitation record for email {}", invitorEmail);
        }
        return isSuccess;
    }
}
