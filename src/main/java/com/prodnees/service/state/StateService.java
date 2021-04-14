package com.prodnees.service.state;

import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.State;
import java.util.List;
import java.util.Optional;

public interface StateService {

    boolean existsByBatchId(int batchId);

    State save(State state);

    State getById(int id);

    State getByName(String name);

    List<State> getAllByBatchId(int batchId);

    List<State> getAllByBatchIdAndStatus(int batchId, StateStatus status);

    void deleteById(int id);

    boolean existsById(int id);

    Optional<State> findById(int id);
}
