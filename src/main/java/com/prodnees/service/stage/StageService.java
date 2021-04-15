package com.prodnees.service.stage;

import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.state.Stage;
import java.util.List;
import java.util.Optional;

public interface StageService {

    boolean existsByBatchId(int batchId);

    Stage save(Stage stage);

    Stage getById(int id);

    Stage getByName(String name);

    List<Stage> getAllByBatchId(int batchId);

    List<Stage> getAllByBatchIdAndState(int batchId, StageState state);

    void deleteById(int id);

    boolean existsById(int id);

    Optional<Stage> findById(int id);
}
