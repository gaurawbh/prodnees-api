package com.prodnees.action.stage;

import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.rels.BatchRight;
import com.prodnees.domain.stage.Stage;
import com.prodnees.model.stage.StageModel;
import java.util.List;
import java.util.Optional;

public interface StageAction {

    boolean existsByBatchId(int batchId);

    /**
     * A user will have the same {@link BatchRight} to a Stage as the {@link Batch} the Stage belongs to
     *
     * @param batchId
     * @param editorId
     * @return
     */
    boolean hasStageEditorRights(int id, int editorId);

    /**
     * A user will have the same {@link BatchRight} to a Stage as the {@link Batch} the Stage belongs to
     *
     * @param id
     * @param readerId
     * @return
     */
    boolean hasStageReaderRights(int id, int readerId);

    boolean existsById(int id);

    StageModel save(Stage stage);

    Optional<Stage> findById(int id);

    Stage getById(int id);

    StageModel getModelById(int id);

    Stage getByName(String name);

    List<StageModel> getAllByBatchId(int batchId);

    List<Stage> getAllByBatchIdAndState(int batchId, StageState state);

    /**
     * Deleting a {@link Stage} should connect its head State and it tail State.
     * <p>Only Owner should be allowed to delete a State</p>
     *
     * @param id
     */
    void deleteById(int id);
}
