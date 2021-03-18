package com.prodnees.dao.rels;

import com.prodnees.domain.rels.ProductRights;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductRightsDao extends JpaRepository<ProductRights, Integer> {

    Optional<ProductRights> findByProductIdAndUserId(int productId, int ownerId);

    List<ProductRights> getAllByUserId(int ownerId);

    List<ProductRights> getAllByProductId(int productId);
}
