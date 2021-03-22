package com.prodnees.action.rel.impl;

import com.prodnees.action.rel.AssociateInvitationAction;
import com.prodnees.domain.rels.AssociateInvitation;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.rels.AssociateInvitationService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class AssociateInvitationActionImpl implements AssociateInvitationAction {
    private final LocalEmailService localEmailService;
    private final AssociateInvitationService associateInvitationService;

    public AssociateInvitationActionImpl(LocalEmailService localEmailService,
                                         AssociateInvitationService associateInvitationService) {
        this.localEmailService = localEmailService;
        this.associateInvitationService = associateInvitationService;
    }

    @Override
    public AssociateInvitation save(AssociateInvitation associateInvitation) {
        return null;
    }

    @Override
    public boolean existsByInvitorIdAndInviteeIdAndAccepted(int invitorId, int inviteeId, boolean accepted) {
        return false;
    }

    @Override
    public Optional<AssociateInvitation> findByInvitorIdAndInviteeId(int invitorId, int inviteeId) {
        return Optional.empty();
    }

    @Override
    public List<AssociateInvitation> getAllByInvitorId(int invitorId) {
        return null;
    }

    @Override
    public List<AssociateInvitation> getAllByInviteeId(int inviteeId) {
        return null;
    }

    @Override
    public boolean sendInvitation(String invitorEmail, String inviteeEmail) {
        return false;
    }
}
