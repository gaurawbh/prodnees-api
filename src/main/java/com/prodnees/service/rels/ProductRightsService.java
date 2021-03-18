package com.prodnees.service.rels;

import com.prodnees.domain.rels.ProductRights;
import java.util.List;
import java.util.Optional;

public interface ProductRightsService {

    ProductRights save(ProductRights productRights);

    Optional<ProductRights> findByProductIdAndUserId(int productId, int ownerId);

    List<ProductRights> getAllByUserId(int ownerId);

    List<ProductRights> getAllByProductId(int productId);
}
