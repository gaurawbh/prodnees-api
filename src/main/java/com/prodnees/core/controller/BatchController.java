package com.prodnees.core.controller;

import com.prodnees.core.action.BatchAction;
import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.enums.BatchState;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.dto.batch.BatchDto;
import com.prodnees.core.model.batch.BatchModel;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.ValidatorUtil;
import com.prodnees.core.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.prodnees.core.web.response.LocalResponse.configure;

@RestController
@Transactional
public class BatchController {
    private final BatchAction batchAction;

    public BatchController(BatchAction batchAction) {
        this.batchAction = batchAction;
    }

    /**
     * On saving a new BatchProduct:
     * <p>save to BatchProductRights</p>
     *
     * @param dto
     * @return
     */

    @PostMapping("/batch")
    public ResponseEntity<?> saveBatch(@Validated @RequestBody BatchDto dto) {
        return configure(batchAction.create(dto));
    }

    /**
     * returns {@link BatchModel} by it's id if id is provided
     * <p>returns list of {@link BatchModel}</p> that belongs to a user if id is not provided
     *
     * @param id
     * @return
     */
    @GetMapping("/batches")
    public ResponseEntity<?> getBatches(@RequestParam Optional<Integer> id) {
        AtomicReference<Object> atomicReference = new AtomicReference<>();

        id.ifPresentOrElse(integer -> atomicReference.set(batchAction.getModelById(integer)), () -> atomicReference.set(batchAction.getBatchList()));
        return configure(atomicReference.get());
    }

    /**
     * @param state of {@link Batch}
     * @return list of {@link BatchModel} that belongs to the User and by {@link BatchState}
     */
    @GetMapping("/batches/state")
    public ResponseEntity<?> getAllByBatchState(@RequestParam BatchState state) {
        return configure(batchAction.getAllByState(state));
    }

    /**
     * Update {@link Batch} -> {@link BatchState}
     *
     * @param state of {@link Batch}
     * @param id    of {@link Batch}
     * @return
     */
    @PutMapping("/batches/state")
    public ResponseEntity<?> updateBatchState(@RequestParam BatchState state,
                                              @RequestParam int id) {
        Batch batch = batchAction.getById(id);
        LocalAssert.isTrue(isValidBatchStateUpdate(batch.getState(), state), "invalid state for the Batch");
        batch.setState(state);
        return LocalResponse.configure(batchAction.save(batch));
    }

    /**
     * Check if the new {@link BatchState} of a  {@link Batch} is valid
     *
     * @param currentState
     * @param newState
     * @return
     */

    boolean isValidBatchStateUpdate(BatchState currentState, BatchState newState) {
        switch (newState) {
            case SUSPENDED:
            case COMPLETE:
                return currentState == BatchState.OPEN
                        || currentState == BatchState.IN_PROGRESS;
            case OPEN:
                return currentState == BatchState.IN_PROGRESS;
            case IN_PROGRESS:
                return currentState == BatchState.COMPLETE
                        || currentState == BatchState.OPEN;
            default:
                return false;
        }
    }


    /**
     * only description can be changed of a Batch.
     * <p>productId on Request Body will be ignored</p>
     * <i>User must have editor rights, i.e. {@link ObjectRight#full}  or {@link ObjectRight#update} </i>
     *
     * @param dto
     * @return
     */
    @PutMapping("/batch")
    public ResponseEntity<?> updateBatch(@Validated @RequestBody BatchDto dto) {
        Batch batch = batchAction.getById(dto.getId());
        batch.setDescription(ValidatorUtil.ifValidStringOrElse(dto.getDescription(), batch.getDescription()));
        return configure(batchAction.save(batch));
    }

    /**
     * <p>check the user has {@link ObjectRight#full} rights of the Batch</p>
     * <p>check the Batch does not have any States or {@link com.prodnees.core.domain.stage.StageTodo} associated with it</p>
     *
     * @param id
     * @return
     */
    @DeleteMapping("/batch")
    public ResponseEntity<?> deleteBatch(@RequestParam int id) {
        batchAction.deleteById(id);
        return configure();
    }

}
