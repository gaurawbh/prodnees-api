package com.prodnees.action.state;

import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.rels.BatchRight;
import com.prodnees.domain.state.State;
import com.prodnees.model.StateModel;
import java.util.List;
import java.util.Optional;

public interface StateAction {

    boolean existsByBatchId(int batchId);

    /**
     * A user will have the same {@link BatchRight} to a State as the {@link Batch} the State belongs to
     *
     * @param batchProductId
     * @param editorId
     * @return
     */
    boolean hasStateEditorRights(int id, int editorId);

    /**
     * A user will have the same {@link BatchRight} to a State as the {@link Batch} the State belongs to
     *
     * @param batchProductId
     * @param readerId
     * @return
     */
    boolean hasStateReaderRights(int id, int readerId);

    boolean existsById(int id);

    StateModel save(State state);

    Optional<State> findById(int id);

    State getById(int id);

    StateModel getModelById(int id);

    State getByName(String name);

    List<StateModel> getAllByBatchId(int batchId);

    List<State> getAllByBatchIdAndStatus(int batchId, StateStatus status);

    /**
     * Deleting a {@link State} should connect its head State and it tail State.
     * <p>Only Owner should be allowed to delete a State</p>
     *
     * @param id
     */
    void deleteById(int id);
}
