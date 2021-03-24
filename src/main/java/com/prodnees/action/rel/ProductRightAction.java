package com.prodnees.action.rel;

import com.prodnees.domain.rels.ProductRight;
import com.prodnees.dto.ProductRightDto;
import com.prodnees.model.ProductRightModel;

import java.util.List;
import java.util.Optional;

public interface ProductRightAction {

    ProductRightModel save(ProductRightDto productRightDto);

    ProductRight save(ProductRight productRight);

    boolean existsByProductIdAndUserId(int productId, int userId);

    Optional<ProductRight> findByProductIdAndUserId(int productId, int userId);

    List<ProductRight> getAllByUserId(int userId);

    List<ProductRight> getAllByProductId(int productId);

    boolean sendNewProductRightsEmail(String email);

    void deleteByProductIdAndUserId(int productId, int userId);
}
