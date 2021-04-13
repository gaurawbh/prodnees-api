package com.prodnees.action.rel;

import com.prodnees.domain.rels.AssociateInvitation;
import java.util.List;
import java.util.Optional;

public interface AssociateInvitationAction {

    AssociateInvitation save(AssociateInvitation associateInvitation);

    boolean existsByInvitorIdAndInviteeIdAndAccepted(int invitorId, int inviteeId, boolean accepted);

    Optional<AssociateInvitation> findByInvitorIdAndInviteeId(int invitorId, int inviteeId);

    Optional<AssociateInvitation> findByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail);

    List<AssociateInvitation> getAllByInvitorId(int invitorId);

    List<AssociateInvitation> getAllByInviteeId(int inviteeId);

    /**
     * If the Invitee is not an application user, add them as a new user
     * <p>send tempo</p>
     *
     * @param invitorEmail
     * @param inviteeEmail
     * @param inviteeIsUser
     * @return
     */
    boolean sendInvitationIfUser(String invitorEmail, String invitorComment, String inviteeEmail, boolean inviteeIsUser);

    /**
     * Send email to the invitor and update the Associate Invitation Record
     *
     * @param invitorEmail
     * @param accept
     * @return
     */
    boolean actionInvitationRequest(String invitorEmail, boolean accept);

    boolean deleteByInvitorEmailAndInviteeEmail(String invitorEmail, String inviteeEmail);
}
