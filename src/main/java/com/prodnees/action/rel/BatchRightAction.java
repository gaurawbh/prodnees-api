package com.prodnees.action.rel;

import com.prodnees.domain.rels.BatchRight;
import com.prodnees.dto.batchproduct.BatchRightDto;
import com.prodnees.model.BatchProductRightModel;
import java.util.List;
import java.util.Optional;

public interface BatchRightAction {

    BatchProductRightModel save(BatchRightDto batchRightDto);

    BatchRight save(BatchRight batchRight);

    Optional<BatchRight> findByBatchIdAndUserId(int batchProductId, int ownerId);

    List<BatchProductRightModel> getAllByBatchId(int batchProductId);

    List<BatchRight> getAllByOwnerId(int ownerId);

    List<BatchProductRightModel> getAllModelByUserId(int ownerId);

    boolean hasBatchEditorRights(int batchProductId, int editorId);

    boolean hasBatchReaderRights(int batchProductId, int readerId);

    boolean sendNewBatchRightsEmail(String email);

    void deleteByBatchIdAndUserId(int batchProductId, int userId);
}
