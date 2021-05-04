package com.prodnees.action.rel;

import com.prodnees.domain.rels.BatchRight;
import com.prodnees.dto.batch.BatchRightDto;
import com.prodnees.model.batch.BatchRightModel;

import java.util.List;
import java.util.Optional;

public interface BatchRightAction {

    BatchRightModel save(BatchRightDto batchRightDto);

    BatchRight save(BatchRight batchRight);

    Optional<BatchRight> findByBatchIdAndUserId(int batchId, int ownerId);

    List<BatchRightModel> getAllByBatchId(int batchId);

    List<BatchRight> getAllByUserId(int ownerId);

    List<BatchRightModel> getAllModelByUserId(int ownerId);

    boolean hasBatchEditorRights(int batchId, int editorId);

    boolean hasBatchReaderRights(int batchId, int readerId);

    boolean sendNewBatchRightsEmail(String email);

    void deleteByBatchIdAndUserId(int batchId, int userId);
}
