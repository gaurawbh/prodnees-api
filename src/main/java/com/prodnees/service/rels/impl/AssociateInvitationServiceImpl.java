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
        return associateInvitationDao.save(associateInvitation);
    }

    @Override
    public boolean existsByInvitorIdAndInviteeIdAndAccepted(int invitorId, int inviteeId, boolean accepted) {
        return associateInvitationDao.existsByInvitorIdAndInviteeIdAndAccepted(invitorId, inviteeId, accepted);
    }

    @Override
    public Optional<AssociateInvitation> findByInvitorIdAndInviteeId(int invitorId, int inviteeId) {
        return associateInvitationDao.findByInvitorIdAndInviteeId(invitorId, inviteeId);
    }

    @Override
    public List<AssociateInvitation> getAllByInvitorId(int invitorId) {
        return associateInvitationDao.getAllByInvitorId(invitorId);
    }

    @Override
    public List<AssociateInvitation> getAllByInviteeId(int inviteeId) {
        return associateInvitationDao.getAllByInviteeId(inviteeId);
    }

    @Override
    public Optional<AssociateInvitation> findByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail) {
        return associateInvitationDao.findByInvitorEmailAndInviteeEmail(invitorEmail, inviteeEmail);
    }

    @Override
    public boolean deleteByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail) {
        return associateInvitationDao.deleteByInvitorEmailAndInviteeEmail(invitorEmail, inviteeEmail);
    }
}
