package com.prodnees.service;

import com.prodnees.domain.State;

public interface StateService {

    State save(State state);

    State getById(int id);

    State getByBatchProductId(int batchProductId);

}
