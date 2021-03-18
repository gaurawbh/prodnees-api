package com.prodnees.action.impl;

import com.prodnees.action.BatchProductAction;
import com.prodnees.domain.BatchProduct;
import com.prodnees.model.BatchProductModel;
import com.prodnees.service.BatchProductService;
import com.prodnees.service.ProductService;
import com.prodnees.service.rels.BatchProductRightsService;
import com.prodnees.service.rels.ProductRightsService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BatchProductActionImpl implements BatchProductAction {

    private final BatchProductService batchProductService;
    private final ProductRightsService productRightsService;
    private final BatchProductRightsService batchProductRightsService;
    private final ProductService productService;

    public BatchProductActionImpl(BatchProductService batchProductService,
                                  ProductRightsService productRightsService,
                                  BatchProductRightsService batchProductRightsService,
                                  ProductService productService) {
        this.batchProductService = batchProductService;
        this.productRightsService = productRightsService;
        this.batchProductRightsService = batchProductRightsService;
        this.productService = productService;
    }

    @Override
    public BatchProductModel save(BatchProduct batchProduct) {
        return mapToModel(batchProductService.save(batchProduct));
    }

    @Override
    public BatchProduct getById(int id) {
        return null;
    }

    @Override
    public List<BatchProduct> getAllByProductId(int productId) {
        return null;
    }

    BatchProductModel mapToModel(BatchProduct batchProduct) {
        BatchProductModel model = new BatchProductModel();
        model.setId(batchProduct.getId())
                .setName(batchProduct.getName())
                .setProduct(productService.getById(batchProduct.getId()))
                .setDescription(batchProduct.getDescription())
                .setCreatedDate(batchProduct.getCreatedDate());
        return model;

    }
}
