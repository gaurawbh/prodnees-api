package com.prodnees.dao.rels;

import com.prodnees.domain.rels.AssociateInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AssociateInvitationDao extends JpaRepository<AssociateInvitation, Integer> {

    boolean existsByInvitorIdAndInviteeIdAndAccepted(int invitorId, int inviteeId, boolean accepted);

    Optional<AssociateInvitation> findByInvitorIdAndInviteeId(int invitorId, int inviteeId);

    List<AssociateInvitation> getAllByInvitorId(int invitorId);

    List<AssociateInvitation> getAllByInviteeId(int inviteeId);

    Optional<AssociateInvitation> findByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail);

    boolean deleteByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail);
}
