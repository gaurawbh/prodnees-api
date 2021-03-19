package com.prodnees.action;

import com.prodnees.domain.rels.ProductRights;
import com.prodnees.dto.ProductRightsDto;
import com.prodnees.model.ProductRightsModel;

import java.util.List;
import java.util.Optional;

public interface ProductRightsAction {

    ProductRightsModel save(ProductRightsDto productRightsDto);

    ProductRights save(ProductRights productRights);

    boolean existsByProductIdAndUserId(int productId, int userId);

    Optional<ProductRights> findByProductIdAndUserId(int productId, int userId);

    List<ProductRights> getAllByUserId(int userId);

    List<ProductRights> getAllByProductId(int productId);
}
