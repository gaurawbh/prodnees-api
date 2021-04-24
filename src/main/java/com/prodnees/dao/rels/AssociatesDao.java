package com.prodnees.dao.rels;

import com.prodnees.domain.rels.Associates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssociatesDao extends JpaRepository<Associates, Integer> {

    Optional<Associates> findByAdminIdAndAssociateId(int adminId, int userId);

    List<Associates> getAllByAdminId(int adminId);

    List<Associates> getAllByAssociateId(int userId);

    Optional<Associates> findByAdminIdAndAssociateEmail(int adminId, String associateEmail);

    boolean existsByAdminIdAndAssociateEmail(int adminId, String associateEmail);
}
