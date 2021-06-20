package com.prodnees.action.impl;

import com.prodnees.action.BatchAction;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.batch.Product;
import com.prodnees.domain.enums.BatchState;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.domain.rels.BatchRight;
import com.prodnees.dto.batch.BatchDto;
import com.prodnees.model.batch.BatchListModel;
import com.prodnees.model.batch.BatchModel;
import com.prodnees.service.batch.BatchService;
import com.prodnees.service.batch.ProductService;
import com.prodnees.service.rels.BatchRightService;
import com.prodnees.util.LocalAssert;
import com.prodnees.util.MapperUtil;
import com.prodnees.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchActionImpl implements BatchAction {

    private final BatchService batchService;
    private final BatchRightService batchRightService;
    private final ProductService productService;

    public BatchActionImpl(BatchService batchService,
                           BatchRightService batchRightService,
                           ProductService productService) {
        this.batchService = batchService;
        this.batchRightService = batchRightService;
        this.productService = productService;
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
    public List<BatchModel> getAllByState(BatchState state) {
        int userId = RequestContext.getUserId();

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
    public BatchModel create(BatchDto dto) {
        LocalAssert.isTrue(productService.existsById(dto.getProductId()), String.format("Product with id: %d not found", dto.getProductId()));
        int userId = RequestContext.getUserId();
        int nextId = batchService.getNextId();
        String batchName = "Batch-" + nextId;

        Batch batch = MapperUtil.getDozer().map(dto, Batch.class);
        batch.setName(batchName).setCreatedDate(LocalDate.now()).setState(BatchState.OPEN);
        batch = batchService.save(batch);
        batchRightService.save(new BatchRight()
                .setUserId(userId)
                .setBatchId(batch.getId())
                .setObjectRight(ObjectRight.OWNER));
        return mapToModel(batch);
    }

    @Override
    public Batch getById(int id) {
        return batchService.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("Batch with id: %d not found", id)));
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
        Product product = productService.getById(batch.getProductId());
        int userId = RequestContext.getUserId();
        model.setRightType(batchRightService.findByBatchIdAndUserId(batch.getId(), userId).get().getObjectRight());

        model.setId(batch.getId())
                .setName(batch.getName())
                .setProduct(product)
                .setStageApprovalDocument(null)
                .setStatus(batch.getState())
                .setDescription(batch.getDescription())
                .setCreatedDate(batch.getCreatedDate());
        return model;

    }
}
