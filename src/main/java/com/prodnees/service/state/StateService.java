package com.prodnees.service.state;

import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.State;
import java.util.List;
import java.util.Optional;

public interface StateService {

    boolean existsByBatchProductId(int batchProductId);

    State save(State state);

    State getById(int id);

    State getByName(String name);

    List<State> getAllByBatchProductId(int batchProductId);

    List<State> getAllByBatchProductIdAndStatus(int batchProductId, StateStatus status);

    void deleteById(int id);

    boolean existsById(int id);

    Optional<State> findById(int id);
}
