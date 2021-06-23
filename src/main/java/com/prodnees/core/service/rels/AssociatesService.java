package com.prodnees.core.service.rels;

import com.prodnees.core.domain.rels.Associates;

import java.util.List;
import java.util.Optional;

public interface AssociatesService {

    Associates save(Associates associates);

    Optional<Associates> findByAdminIdAndAssociateId(int adminId, int userId);

    Optional<Associates> findByAdminIdAndAssociateEmail(int adminId, String associateEmail);

    boolean existsByAdminIdAndAssociateEmail(int adminId, String associateEmail);

    List<Associates> getAllByAdminId(int adminId);

    List<Associates> getAllByAssociateId(int userId);
}
