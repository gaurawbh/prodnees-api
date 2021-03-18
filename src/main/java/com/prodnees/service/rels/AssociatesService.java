package com.prodnees.service.rels;

import com.prodnees.domain.rels.Associates;

import java.util.List;
import java.util.Optional;

public interface AssociatesService {

    Associates save(Associates associates);

    Optional<Associates> findByAdminIdAndUserId(int adminId, int userId);

    List<Associates> getAllByAdminId(int adminId);

    List<Associates> getAllByUserId(int userId);
}
