package com.prodnees.service.rels;

import com.prodnees.domain.rels.ProductRight;

import java.util.List;
import java.util.Optional;

public interface ProductRightsService {

    ProductRight save(ProductRight productRight);

    boolean existsByProductIdAndUserId(int productId, int userId);

    Optional<ProductRight> findByProductIdAndUserId(int productId, int userId);

    List<ProductRight> getAllByUserId(int userId);

    List<ProductRight> getAllByProductId(int productId);

    void deleteByProductIdAndUserId(int productId, int userId);

    boolean hasProductEditorRight(int productId, int userId);
}
