package com.prodnees.action.rel;

import com.prodnees.domain.rels.BatchProductRight;
import com.prodnees.dto.BatchProductRightDto;
import com.prodnees.model.BatchProductRightModel;
import java.util.List;
import java.util.Optional;

public interface BatchProductRightAction {

    BatchProductRightModel save(BatchProductRightDto batchProductRightDto);

    BatchProductRight save(BatchProductRight batchProductRight);

    Optional<BatchProductRight> findByBatchProductIdAndOwnerId(int batchProductId, int ownerId);

    List<BatchProductRightModel> getAllByBatchProductId(int batchProductId);

    List<BatchProductRight> getAllByOwnerId(int ownerId);

    List<BatchProductRightModel> getAllModelByOwnerId(int ownerId);

    boolean hasBatchProductEditorRights(int batchProductId, int editorId);

    boolean hasBatchProductReaderRights(int batchProductId, int readerId);

    boolean sendNewBatchProductRightsEmail(String email);
}
