package com.prodnees.core.action.stage;

import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.Stage;
import com.prodnees.core.dto.stage.StageDto;
import com.prodnees.core.model.stage.StageModel;

import java.util.List;
import java.util.Optional;

public interface StageAction {

    boolean existsByBatchId(int batchId);

    boolean existsById(int id);

    StageModel addNew(StageDto stage);

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
