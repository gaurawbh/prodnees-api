package com.prodnees.action.impl;

import com.prodnees.action.BatchAction;
import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.model.BatchProductModel;
import com.prodnees.model.ProductModel;
import com.prodnees.service.batchproduct.BatchService;
import com.prodnees.service.batchproduct.ProductService;
import com.prodnees.service.rels.BatchRightService;
import com.prodnees.service.rels.ProductRightsService;
import com.prodnees.util.MapperUtil;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchActionImpl implements BatchAction {

    private final BatchService batchService;
    private final ProductRightsService productRightsService;
    private final BatchRightService batchRightService;
    private final ProductService productService;

    public BatchActionImpl(BatchService batchService,
                           ProductRightsService productRightsService,
                           BatchRightService batchRightService,
                           ProductService productService) {
        this.batchService = batchService;
        this.productRightsService = productRightsService;
        this.batchRightService = batchRightService;
        this.productService = productService;
    }

    @Override
    public boolean existsById(int id) {
        return batchService.existsById(id);
    }

    @Override
    public boolean existsByIdAndStatus(int id, BatchStatus status) {
        return batchService.existsByIdAndStatus(id, status);
    }

    @Override
    public boolean isEditable(int id) {
        return existsByIdAndStatus(id, BatchStatus.COMPLETE) || existsByIdAndStatus(id, BatchStatus.SUSPENDED);
    }

    @Override
    public List<BatchProductModel> getAllByUserIdAndStatus(int userId, BatchStatus status) {
        List<Batch> batchList = batchService.getAllByUserIdAndStatus(userId, status);
        List<BatchProductModel> batchProductModelList = new ArrayList<>();
        batchList.forEach(batchProduct -> batchProductModelList.add(mapToModel(batchProduct)));
        return batchProductModelList;
    }

    @Override
    public BatchProductModel save(Batch batch) {
        return mapToModel(batchService.save(batch));
    }

    @Override
    public Batch getById(int id) {
        return batchService.getById(id);
    }

    @Override
    public BatchProductModel getModelById(int id) {
        return mapToModel(batchService.getById(id));
    }

    @Override
    public List<Batch> getAllByProductId(int productId) {
        return batchService.getAllByProductId(productId);
    }

    @Override
    public List<BatchProductModel> getAllByIds(Iterable<Integer> batchProductIds) {
        List<Batch> batchList = batchService.getAllByIds(batchProductIds);
        List<BatchProductModel> batchProductModelList = new ArrayList<>();
        batchList.forEach(batchProduct -> batchProductModelList.add(mapToModel(batchProduct)));
        return batchProductModelList;
    }

    @Override
    public void deleteById(int id) {
        batchService.deleteById(id);
    }

    BatchProductModel mapToModel(Batch batch) {
        BatchProductModel model = new BatchProductModel();
        ProductModel productModel = MapperUtil.getDozer().map(productService.getById(batch.getProductId()), ProductModel.class);
        model.setId(batch.getId())
                .setName(batch.getName())
                .setProductModel(productModel)
                .setApprovalDocumentModel(null)
                .setStatus(batch.getStatus())
                .setDescription(batch.getDescription())
                .setCreatedDate(batch.getCreatedDate());
        return model;

    }
}
