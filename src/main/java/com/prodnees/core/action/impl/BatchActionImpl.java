package com.prodnees.core.action.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.BatchAction;
import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.enums.BatchState;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.dto.batch.BatchDto;
import com.prodnees.core.model.batch.BatchListModel;
import com.prodnees.core.model.batch.BatchModel;
import com.prodnees.core.service.batch.BatchService;
import com.prodnees.core.service.user.NeesObjectRightService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.MapperUtil;
import com.prodnees.core.web.exception.NeesBadRequestException;
import com.prodnees.core.web.exception.NeesNotFoundException;
import com.prodnees.shelf.domain.Product;
import com.prodnees.shelf.service.ProductService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchActionImpl implements BatchAction {

    private final BatchService batchService;
    private final ProductService productService;
    private final NeesObjectRightService neesObjectRightService;

    public BatchActionImpl(BatchService batchService,
                           ProductService productService,
                           NeesObjectRightService neesObjectRightService) {
        this.batchService = batchService;
        this.productService = productService;
        this.neesObjectRightService = neesObjectRightService;
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
        verifyRight(ObjectRight.update);
        return mapToModel(batchService.save(batch));
    }

    @Override
    public BatchModel create(BatchDto dto) {
        LocalAssert.isTrue(productService.existsById(dto.getProductId()), String.format("Product with id: %d not found", dto.getProductId()));
        verifyRight(ObjectRight.update);
        int nextId = batchService.getNextId();
        String batchName = "Batch-" + nextId;
        Batch batch = MapperUtil.getDozer().map(dto, Batch.class);
        batch.setName(batchName).setCreatedDate(LocalDate.now()).setState(BatchState.OPEN);
        batch = batchService.save(batch);
        return mapToModel(batch);
    }

    @Override
    public Batch getById(int id) {
        verifyRight(ObjectRight.viewOnly);
        return batchService.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("Batch with id: %d not found", id)));
    }

    @Override
    public BatchModel getModelById(int id) {
        return mapToModel(getById(id));
    }

    @Override
    public List<Batch> getAllByProductId(int productId) {
        verifyRight(ObjectRight.viewOnly);
        return batchService.getAllByProductId(productId);
    }

    @Override
    public List<Batch> findAll() {
        verifyRight(ObjectRight.viewOnly);

        return batchService.findAll();
    }

    @Override
    public BatchListModel getBatchList() {
        verifyRight(ObjectRight.viewOnly);

        List<Batch> batchList = batchService.findAll();
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
        verifyRight(ObjectRight.full);

        batchService.deleteById(id);
    }

    BatchModel mapToModel(Batch batch) {
        BatchModel model = new BatchModel();
        Product product = productService.getById(batch.getProductId());
        int userId = RequestContext.getUserId();
        model.setRightType(neesObjectRightService.getByUserIdAndNeesObject(userId, NeesObject.batch).getObjectRight());

        model.setId(batch.getId())
                .setName(batch.getName())
                .setProduct(product)
                .setStatus(batch.getState())
                .setDescription(batch.getDescription())
                .setCreatedDate(batch.getCreatedDate());
        return model;

    }

    private void verifyRight(ObjectRight objectRight) {
        switch (objectRight) {
            case full:
                LocalAssert.isTrue(neesObjectRightService.hasFullObjectRight(RequestContext.getUserId(), NeesObject.batch),
                        "Insufficient right to delete Batch");
            case update:
                LocalAssert.isTrue(neesObjectRightService.hasUpdateObjectRight(RequestContext.getUserId(), NeesObject.batch),
                        "Insufficient right to add or update Batch");
            case viewOnly:
                LocalAssert.isTrue(neesObjectRightService.hasFullObjectRight(RequestContext.getUserId(), NeesObject.batch),
                        "Insufficient right to delete Batch");
            default:
                throw new NeesBadRequestException("Insufficient right to Batch");
        }
    }

}
