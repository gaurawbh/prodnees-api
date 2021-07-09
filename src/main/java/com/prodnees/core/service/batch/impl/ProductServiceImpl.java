package com.prodnees.core.service.batch.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.dao.batch.ProductDao;
import com.prodnees.core.dao.rels.ProductRightsDao;
import com.prodnees.core.domain.batch.Product;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.rels.ProductRight;
import com.prodnees.core.dto.ProductDto;
import com.prodnees.core.service.batch.ProductService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.MapperUtil;
import com.prodnees.core.util.ValidatorUtil;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final ProductRightsDao productRightsDao;

    public ProductServiceImpl(ProductDao productDao,
                              ProductRightsDao productRightsDao) {
        this.productDao = productDao;
        this.productRightsDao = productRightsDao;
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
        productRightsDao.save(new ProductRight()
                .setUserId(ownerId)
                .setProductId(product.getId())
                .setObjectRightsType(ObjectRight.Owner));
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
        Product product = productDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("Product with id: %d not found", id)));
        LocalAssert.isTrue(productRightsDao.existsByProductIdAndUserId(id, RequestContext.getUserId()), String.format("Product with id: %d not found", id));
        return product;
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

    @Override
    public boolean existsById(int id) {
        return productDao.existsById(id);
    }
}
