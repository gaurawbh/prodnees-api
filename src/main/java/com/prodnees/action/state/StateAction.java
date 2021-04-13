package com.prodnees.action.state;

import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.State;
import com.prodnees.model.StateModel;
import java.util.List;
import java.util.Optional;

public interface StateAction {

    boolean existsByBatchProductId(int batchProductId);

    boolean existsById(int id);

    StateModel save(State state);

    Optional<State> findById(int id);

    State getById(int id);

    StateModel getModelById(int id);

    State getByName(String name);

    List<StateModel> getAllByBatchProductId(int batchProductId);

    List<State> getAllByBatchProductIdAndStatus(int batchProductId, StateStatus status);

    /**
     * Deleting a {@link State} should connect its head State and it tail State.
     * <p>Only Owner should be allowed to delete a State</p>
     *
     * @param id
     */
    void deleteById(int id);
}
