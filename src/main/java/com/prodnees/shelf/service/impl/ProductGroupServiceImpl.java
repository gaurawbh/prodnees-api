package com.prodnees.shelf.service.impl;

import com.prodnees.core.util.LocalStringUtils;
import com.prodnees.core.web.exception.NeesBadRequestException;
import com.prodnees.core.web.exception.NeesNotFoundException;
import com.prodnees.shelf.dao.ProductGroupDao;
import com.prodnees.shelf.domain.ProductGroup;
import com.prodnees.shelf.domain.ProductGroupEnum;
import com.prodnees.shelf.service.ProductGroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductGroupServiceImpl implements ProductGroupService {
    private final ProductGroupDao productGroupDao;

    public ProductGroupServiceImpl(ProductGroupDao productGroupDao) {
        this.productGroupDao = productGroupDao;
    }

    @Override
    public ProductGroup getById(int id) {
        return productGroupDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("Product Group with id: %d not found", id)));
    }

    @Override
    public List<ProductGroup> findAll() {
        List<ProductGroup> productGroups = productGroupDao.findAll();
        if (productGroups.isEmpty()) {
            ProductGroupEnum[] productGroupEnums = ProductGroupEnum.values();
            for (ProductGroupEnum groupEnum : productGroupEnums) {
                ProductGroup productGroup = new ProductGroup()
                        .setPrivateKey(groupEnum.name())
                        .setLabel(groupEnum.getLabel())
                        .setDescription(groupEnum.getDescription())
                        .setActive(groupEnum.isActive());
                productGroupDao.save(productGroup);
            }
            productGroups.addAll(productGroupDao.findAll());
        }
        return productGroups;
    }

    @Override
    public ProductGroup addProductGroup(ProductGroup productGroup) {
        productGroup.setPrivateKey(LocalStringUtils.toLowerCamelCase(productGroup.getLabel()));
        return productGroupDao.save(productGroup);
    }

    @Override
    public void deleteById(int id) {
        ProductGroup productGroup = getById(id);
        if (productGroup.isSys()) {
            throw new NeesBadRequestException("Cannot delete system Product Group");
        }
        productGroupDao.deleteById(id);
    }

    @Override
    public ProductGroup updateProductGroup(ProductGroup dto) {
        ProductGroup productGroup = getById(dto.getId());
        productGroup.setLabel(dto.getLabel())
                .setDescription(dto.getDescription());
        return productGroupDao.save(productGroup);
    }


}
