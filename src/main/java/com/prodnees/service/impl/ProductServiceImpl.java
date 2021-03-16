package com.prodnees.service.impl;

import com.prodnees.dao.ProductDao;
import com.prodnees.domain.Product;
import com.prodnees.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public Product save(Product product) {
        return productDao.save(product);
    }

    @Override
    public Product getById(int id) {
        return null;
    }

    @Override
    public Product getByName(String name) {
        return null;
    }
}
