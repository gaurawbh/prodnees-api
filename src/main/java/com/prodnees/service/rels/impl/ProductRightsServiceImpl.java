package com.prodnees.service.rels.impl;

import com.prodnees.dao.rels.ProductRightsDao;
import com.prodnees.domain.rels.ProductRights;
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
    public ProductRights save(ProductRights productRights) {
        return productRightsDao.save(productRights);
    }

    @Override
    public boolean existsByProductIdAndUserId(int productId, int userId) {
        return productRightsDao.existsByProductIdAndUserId(productId, userId);
    }

    @Override
    public Optional<ProductRights> findByProductIdAndUserId(int productId, int ownerId) {
        return productRightsDao.findByProductIdAndUserId(productId, ownerId);
    }

    @Override
    public List<ProductRights> getAllByUserId(int ownerId) {
        return productRightsDao.getAllByUserId(ownerId);
    }

    @Override
    public List<ProductRights> getAllByProductId(int productId) {
        return productRightsDao.getAllByProductId(productId);
    }
}
