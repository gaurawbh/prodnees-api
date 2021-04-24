package com.prodnees.action.impl;

import com.prodnees.action.BatchAction;
import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.BatchState;
import com.prodnees.filter.RequestValidator;
import com.prodnees.model.ProductModel;
import com.prodnees.model.batch.BatchListModel;
import com.prodnees.model.batch.BatchModel;
import com.prodnees.service.batch.BatchService;
import com.prodnees.service.batch.ProductService;
import com.prodnees.service.rels.BatchRightService;
import com.prodnees.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchActionImpl implements BatchAction {

    private final BatchService batchService;
    private final BatchRightService batchRightService;
    private final ProductService productService;
    private final RequestValidator requestValidator;

    public BatchActionImpl(BatchService batchService,
                           BatchRightService batchRightService,
                           ProductService productService,
                           RequestValidator requestValidator) {
        this.batchService = batchService;
        this.batchRightService = batchRightService;
        this.productService = productService;
        this.requestValidator = requestValidator;
    }

    @Override
    public boolean existsById(int id) {
        return batchService.existsById(id);
    }

    @Override
    public boolean existsByIdAndState(int id, BatchState state) {
        return batchService.existsByIdAndState(id, state);
    }

    @Override
    public boolean isEditable(int id) {
        return existsByIdAndState(id, BatchState.COMPLETE) || existsByIdAndState(id, BatchState.SUSPENDED);
    }

    @Override
    public List<BatchModel> getAllByUserIdAndState(int userId, BatchState state) {
        List<Batch> batchList = batchService.getAllByUserIdAndState(userId, state);
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
    public List<Batch> getAllByIds(Iterable<Integer> ids) {
        return batchService.getAllByIds(ids);
    }

    @Override
    public BatchListModel getListModelByIds(Iterable<Integer> ids) {
        List<Batch> batchList = batchService.getAllByIds(ids);
        return new BatchListModel()
                .setBatches(batchList)
                .setCount(batchList.size())
                .setOpen((int) batchList.stream().filter(batch -> batch.getState().equals(BatchState.OPEN)).count())
                .setInProgress((int) batchList.stream().filter(batch -> batch.getState().equals(BatchState.IN_PROGRESS)).count())
                .setComplete((int) batchList.stream().filter(batch -> batch.getState().equals(BatchState.COMPLETE)).count())
                .setSuspended((int) batchList.stream().filter(batch -> batch.getState().equals(BatchState.SUSPENDED)).count());

    }

    @Override
    public void deleteById(int id) {
        batchService.deleteById(id);
    }

    BatchModel mapToModel(Batch batch) {
        BatchModel model = new BatchModel();
        ProductModel productModel = MapperUtil.getDozer().map(productService.getById(batch.getProductId()), ProductModel.class);
        try {
            int userId = requestValidator.extractUserId();
            model.setRightType(batchRightService.findByBatchIdAndUserId(batch.getId(), userId).get().getObjectRightsType());
        } catch (NullPointerException ignored) {

        }
        model.setId(batch.getId())
                .setName(batch.getName())
                .setProductModel(productModel)
                .setApprovalDocumentModel(null)
                .setStatus(batch.getState())
                .setDescription(batch.getDescription())
                .setCreatedDate(batch.getCreatedDate());
        return model;

    }
}
