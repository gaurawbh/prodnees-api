package com.prodnees.service;

import com.prodnees.domain.State;
import java.util.List;

public interface StateService {

    boolean existsByBatchProductId(int batchProductId);

    State save(State state);

    State getById(int id);

    State getByName(String name);

    List<State> getAllByBatchProductId(int batchProductId);

    List<State> getAllByBatchProductIdAndComplete(int batchProductId, boolean isComplete);

}
