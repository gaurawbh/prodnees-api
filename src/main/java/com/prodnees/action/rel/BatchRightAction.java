package com.prodnees.action.rel;

import com.prodnees.domain.rels.BatchRight;
import com.prodnees.dto.batch.BatchRightDto;
import com.prodnees.model.BatchRightModel;
import java.util.List;
import java.util.Optional;

public interface BatchRightAction {

    BatchRightModel save(BatchRightDto batchRightDto);

    BatchRight save(BatchRight batchRight);

    Optional<BatchRight> findByBatchIdAndUserId(int batchProductId, int ownerId);

    List<BatchRightModel> getAllByBatchId(int batchProductId);

    List<BatchRight> getAllByUserId(int ownerId);

    List<BatchRightModel> getAllModelByUserId(int ownerId);

    boolean hasBatchEditorRights(int batchProductId, int editorId);

    boolean hasBatchReaderRights(int batchProductId, int readerId);

    boolean sendNewBatchRightsEmail(String email);

    void deleteByBatchIdAndUserId(int batchProductId, int userId);
}
