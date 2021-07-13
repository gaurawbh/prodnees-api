package com.prodnees.shelf.service.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.dto.ProductDto;
import com.prodnees.core.util.MapperUtil;
import com.prodnees.core.util.ValidatorUtil;
import com.prodnees.core.web.exception.NeesNotFoundException;
import com.prodnees.shelf.dao.ProductDao;
import com.prodnees.shelf.domain.Product;
import com.prodnees.shelf.service.ProductService;
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
    public Product addNew(ProductDto dto) {
        int ownerId = RequestContext.getUserId();
        Product product = MapperUtil.getDozer().map(dto, Product.class);
        product = productDao.save(product);
        return product;
    }

    @Override
    public Product update(ProductDto dto) {
        Product product = getById(dto.getId());
        product.setName(dto.getName())
                .setDescription(ValidatorUtil.ifValidStringOrElse(dto.getDescription(), product.getDescription()));
        return productDao.save(product);
    }

    @Override
    public Product getById(int id) {
        return productDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("Product with id: %d not found", id)));
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
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public void deleteById(int id) {
        productDao.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return productDao.existsById(id);
    }
}
