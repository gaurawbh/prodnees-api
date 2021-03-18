package com.prodnees.service.rels;

import com.prodnees.domain.rels.ProductRights;

import java.util.List;
import java.util.Optional;

public interface ProductRightsService {

    ProductRights save(ProductRights productRights);

    boolean existsByProductIdAndUserId(int productId, int userId);

    Optional<ProductRights> findByProductIdAndUserId(int productId, int userId);

    List<ProductRights> getAllByUserId(int userId);

    List<ProductRights> getAllByProductId(int productId);
}
