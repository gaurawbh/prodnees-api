package com.prodnees.dao.rels;

import com.prodnees.domain.rels.Associates;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssociatesDao extends JpaRepository<Associates, Integer> {

    Optional<Associates> findByUserIdAndAdminId(int userId, int adminId);

    List<Associates> getAllByAdminId(int adminId);

    List<Associates> getAllByUserId(int userId);

}
