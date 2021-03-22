package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.AssociateInvitationDao;
import com.prodnees.domain.rels.AssociateInvitation;
import com.prodnees.service.rels.AssociateInvitationService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AssociateInvitationServiceImpl implements AssociateInvitationService {
    private final AssociateInvitationDao associateInvitationDao;

    public AssociateInvitationServiceImpl(AssociateInvitationDao associateInvitationDao) {
        this.associateInvitationDao = associateInvitationDao;
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
}
