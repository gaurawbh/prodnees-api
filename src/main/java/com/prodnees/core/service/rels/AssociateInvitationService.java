package com.prodnees.core.service.rels;

import com.prodnees.core.domain.rels.AssociateInvitation;

import java.util.List;
import java.util.Optional;

public interface AssociateInvitationService {

    AssociateInvitation save(AssociateInvitation associateInvitation);

    boolean existsByInvitorIdAndInviteeIdAndAccepted(int invitorId, int inviteeId, boolean accepted);

    Optional<AssociateInvitation> findByInvitorIdAndInviteeId(int invitorId, int inviteeId);

    List<AssociateInvitation> getAllByInvitorId(int invitorId);

    List<AssociateInvitation> getAllByInviteeId(int inviteeId);

    Optional<AssociateInvitation> findByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail);

    boolean deleteByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail);
}
