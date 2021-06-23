package com.prodnees.core.action.rel;

import com.prodnees.core.domain.rels.BatchRight;
import com.prodnees.core.dto.batch.BatchRightDto;
import com.prodnees.core.model.batch.BatchRightModel;

import java.util.List;

public interface BatchRightAction {

    BatchRightModel save(BatchRightDto batchRightDto);

    BatchRight save(BatchRight batchRight);

    BatchRight getByBatchIdAndUserId(int batchId, int ownerId);

    List<BatchRightModel> getAllByBatchId(int batchId);

    List<BatchRight> getAllByUserId(int ownerId);

    List<BatchRightModel> getAllModelByUserId(int ownerId);

    boolean hasBatchEditorRights(int batchId, int editorId);

    boolean hasBatchReaderRights(int batchId, int readerId);

    boolean sendNewBatchRightsEmail(String email);

    void deleteByBatchIdAndUserId(int batchId, int userId);
}
