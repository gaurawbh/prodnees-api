package com.prodnees.action.impl;

import com.prodnees.action.BatchAction;
import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.model.BatchModel;
import com.prodnees.model.ProductModel;
import com.prodnees.service.batch.BatchService;
import com.prodnees.service.batch.ProductService;
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
    public List<BatchModel> getAllByUserIdAndStatus(int userId, BatchStatus status) {
        List<Batch> batchList = batchService.getAllByUserIdAndStatus(userId, status);
        List<BatchModel> batchModelList = new ArrayList<>();
        batchList.forEach(batchProduct -> batchModelList.add(mapToModel(batchProduct)));
        return batchModelList;
    }

    @Override
    public BatchModel save(Batch batch) {
        return mapToModel(batchService.save(batch));
    }

    @Override
    public Batch getById(int id) {
        return batchService.getById(id);
    }

    @Override
    public BatchModel getModelById(int id) {
        return mapToModel(batchService.getById(id));
    }

    @Override
    public List<Batch> getAllByProductId(int productId) {
        return batchService.getAllByProductId(productId);
    }

    @Override
    public List<BatchModel> getAllByIds(Iterable<Integer> batchProductIds) {
        List<Batch> batchList = batchService.getAllByIds(batchProductIds);
        List<BatchModel> batchModelList = new ArrayList<>();
        batchList.forEach(batchProduct -> batchModelList.add(mapToModel(batchProduct)));
        return batchModelList;
    }

    @Override
    public void deleteById(int id) {
        batchService.deleteById(id);
    }

    BatchModel mapToModel(Batch batch) {
        BatchModel model = new BatchModel();
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
