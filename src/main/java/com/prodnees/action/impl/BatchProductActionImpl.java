package com.prodnees.action.impl;

import com.prodnees.action.BatchProductAction;
import com.prodnees.domain.BatchProduct;
import com.prodnees.model.BatchProductModel;
import com.prodnees.model.ProductModel;
import com.prodnees.service.BatchProductService;
import com.prodnees.service.ProductService;
import com.prodnees.service.rels.BatchProductRightService;
import com.prodnees.service.rels.ProductRightsService;
import com.prodnees.util.MapperUtil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchProductActionImpl implements BatchProductAction {

    private final BatchProductService batchProductService;
    private final ProductRightsService productRightsService;
    private final BatchProductRightService batchProductRightService;
    private final ProductService productService;

    public BatchProductActionImpl(BatchProductService batchProductService,
                                  ProductRightsService productRightsService,
                                  BatchProductRightService batchProductRightService,
                                  ProductService productService) {
        this.batchProductService = batchProductService;
        this.productRightsService = productRightsService;
        this.batchProductRightService = batchProductRightService;
        this.productService = productService;
    }

    @Override
    public boolean existsById(int id) {
        return batchProductService.existsById(id);
    }

    @Override
    public BatchProductModel save(BatchProduct batchProduct) {
        return mapToModel(batchProductService.save(batchProduct));
    }

    @Override
    public BatchProduct getById(int id) {
        return batchProductService.getById(id);
    }

    @Override
    public BatchProductModel getModelById(int id) {
        return mapToModel(batchProductService.getById(id));
    }

    @Override
    public List<BatchProduct> getAllByProductId(int productId) {
        return batchProductService.getAllByProductId(productId);
    }

    @Override
    public List<BatchProductModel> getAllByIds(Iterable<Integer> batchProductIds) {
        List<BatchProduct> batchProductList = batchProductService.getAllByIds(batchProductIds);
        List<BatchProductModel> batchProductModelList = new ArrayList<>();
        batchProductList.forEach(batchProduct -> batchProductModelList.add(mapToModel(batchProduct)));
        return batchProductModelList;
    }

    @Override
    public void deleteById(int id) {
        batchProductService.deleteById(id);
    }

    BatchProductModel mapToModel(BatchProduct batchProduct) {
        BatchProductModel model = new BatchProductModel();
        ProductModel productModel = MapperUtil.getDozer().map(productService.getById(batchProduct.getProductId()), ProductModel.class);
        model.setId(batchProduct.getId())
                .setName(batchProduct.getName())
                .setProductModel(productModel)
                .setStatus(batchProduct.getStatus())
                .setDescription(batchProduct.getDescription())
                .setCreatedDate(batchProduct.getCreatedDate());
        return model;

    }
}
