package com.prodnees.service.impl;

import com.prodnees.dao.ProductDao;
import com.prodnees.domain.Product;
import com.prodnees.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;

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
        return productDao.getById(id);
    }

    @Override
    public Product getByName(String name) {
        return productDao.getByName(name);
    }

    @Override
    public List<Product> getAllByIds(Iterable<Integer> productIdIterable) {
        return productDao.findAllById(productIdIterable);
    }

    @Override
    public void deleteById(int id) {
        productDao.deleteById(id);
    }
}
