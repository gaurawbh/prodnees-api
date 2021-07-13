package com.prodnees.shelf.service.impl;

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
        productGroup.setPrivateKey(toCamelCase(productGroup.getLabel()));
        return productGroupDao.save(productGroup);
    }

    String toCamelCase(String originalStr) {
        String alphaNumericStr = originalStr.replaceAll("[^A-Za-z0-9]", " ");
        String[] strArray = alphaNumericStr.split(" ");
        StringBuilder camelCaseBuilder = new StringBuilder();
        for (int i = 0; i < strArray.length; i++) {
            if (i == 0) {
                camelCaseBuilder.append(strArray[i].toLowerCase().strip());
            } else {
                camelCaseBuilder.append(strArray[i].toLowerCase().replaceFirst("[a-z0-9]", strArray[i].substring(0, 1).toUpperCase()));
            }

        }
        return camelCaseBuilder.toString();
    }


}
