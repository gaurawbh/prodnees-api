package com.prodnees.core.action.rel;

import com.prodnees.core.domain.rels.ProductRight;
import com.prodnees.core.dto.ProductRightDto;
import com.prodnees.core.model.ProductRightModel;

import java.util.List;
import java.util.Optional;

public interface ProductRightAction {

    ProductRightModel save(ProductRightDto productRightDto);

    ProductRight save(ProductRight productRight);

    boolean existsByProductIdAndUserId(int productId, int userId);

    Optional<ProductRight> findByProductIdAndUserId(int productId, int userId);

    ProductRight getByProductIdAndUserId(int productId, int userId);

    List<ProductRight> getAllByUserId(int userId);

    List<ProductRight> getAllByProductId(int productId);

    boolean sendNewProductRightsEmail(String email);

    boolean hasProductEditorRight(int productId, int userId);

    void deleteByProductIdAndUserId(int productId, int userId);
}
