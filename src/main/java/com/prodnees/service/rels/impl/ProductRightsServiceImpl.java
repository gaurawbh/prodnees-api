package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.ProductRightsDao;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.domain.rels.ProductRight;
import com.prodnees.service.rels.ProductRightsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductRightsServiceImpl implements ProductRightsService {

    private final ProductRightsDao productRightsDao;

    public ProductRightsServiceImpl(ProductRightsDao productRightsDao) {
        this.productRightsDao = productRightsDao;
    }

    @Override
    public ProductRight save(ProductRight productRight) {
        return productRightsDao.save(productRight);
    }

    @Override
    public boolean existsByProductIdAndUserId(int productId, int userId) {
        return productRightsDao.existsByProductIdAndUserId(productId, userId);
    }

    @Override
    public Optional<ProductRight> findByProductIdAndUserId(int productId, int ownerId) {
        return productRightsDao.findByProductIdAndUserId(productId, ownerId);
    }

    @Override
    public List<ProductRight> getAllByUserId(int ownerId) {
        return productRightsDao.getAllByUserId(ownerId);
    }

    @Override
    public List<ProductRight> getAllByProductId(int productId) {
        return productRightsDao.getAllByProductId(productId);
    }

    @Override
    public void deleteByProductIdAndUserId(int productId, int userId) {
        productRightsDao.deleteByProductIdAndUserId(productId, userId);
    }

    @Override
    public boolean hasProductEditorRight(int productId, int userId) {
        Optional<ProductRight> productRightOptional = productRightsDao.findByProductIdAndUserId(productId, userId);
        if (productRightOptional.isEmpty()) {
            return false;
        }
        return productRightOptional.get().getObjectRightsType().equals(ObjectRight.OWNER)
                || productRightOptional.get().getObjectRightsType().equals(ObjectRight.EDITOR);
    }
}
