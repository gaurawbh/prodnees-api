package com.prodnees.dao.rels;

import com.prodnees.domain.rels.ProductRight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRightsDao extends JpaRepository<ProductRight, Integer> {

    Optional<ProductRight> findByProductIdAndUserId(int productId, int ownerId);

    List<ProductRight> getAllByUserId(int ownerId);

    List<ProductRight> getAllByProductId(int productId);

    boolean existsByProductIdAndUserId(int productId, int userId);

    void deleteByProductIdAndUserId(int productId, int userId);
}
