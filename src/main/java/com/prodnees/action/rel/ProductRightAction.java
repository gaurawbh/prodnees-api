package com.prodnees.action.rel;

import com.prodnees.domain.rels.ProductRights;
import com.prodnees.dto.ProductRightDto;
import com.prodnees.model.ProductRightModel;
import java.util.List;
import java.util.Optional;

public interface ProductRightAction {

    ProductRightModel save(ProductRightDto productRightDto);

    ProductRights save(ProductRights productRights);

    boolean existsByProductIdAndUserId(int productId, int userId);

    Optional<ProductRights> findByProductIdAndUserId(int productId, int userId);

    List<ProductRights> getAllByUserId(int userId);

    List<ProductRights> getAllByProductId(int productId);

    boolean sendNewProductRightsEmail(String email);
}
