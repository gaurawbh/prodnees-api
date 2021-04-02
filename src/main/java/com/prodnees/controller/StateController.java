package com.prodnees.controller;

import com.prodnees.action.BatchProductAction;
import com.prodnees.action.EventAction;
import com.prodnees.action.StateAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.Event;
import com.prodnees.domain.State;
import com.prodnees.domain.batchproduct.BatchProduct;
import com.prodnees.domain.enums.BatchProductStatus;
import com.prodnees.dto.StateDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.model.StateModel;
import com.prodnees.service.rels.BatchProductRightService;
import com.prodnees.util.LocalAssert;
import com.prodnees.util.MapperUtil;
import com.prodnees.web.exception.NeesInfoException;
import com.prodnees.web.exception.NeesNotFoundException;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure")
@CrossOrigin
public class StateController {
    private final RequestValidator requestValidator;
    private final BatchProductRightService batchProductRightService;
    private final EventAction eventAction;
    private final StateAction stateAction;
    private final BatchProductAction batchProductAction;

    public StateController(RequestValidator requestValidator,
                           BatchProductRightService batchProductRightService,
                           EventAction eventAction,
                           StateAction stateAction,
                           BatchProductAction batchProductAction) {
        this.requestValidator = requestValidator;
        this.batchProductRightService = batchProductRightService;
        this.eventAction = eventAction;
        this.stateAction = stateAction;
        this.batchProductAction = batchProductAction;
    }

    /**
     * Rules to Adding new  {@link State} to a {@link BatchProduct}
     * <p>if {@link State#nextStateId} > 0, the {@link State} can not be the finalState</p>
     * <p>if  {@link State#lastStateId} > 0,  the {@link State} must not be the initialState</p>
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PostMapping("/state")
    public ResponseEntity<?> save(@Validated @RequestBody StateDto dto,
                                  HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(batchProductRightService.hasBatchProductEditorRights(dto.getBatchProductId(), userId),
                APIErrors.BATCH_PRODUCT_NOT_FOUND);
        LocalAssert.isTrue(batchProductAction.existsByIdAndStatus(dto.getBatchProductId(), BatchProductStatus.COMPLETE), "you cannot add a State to a Batch Product that is marked as Complete");
        if (dto.getNextStateId() > 0) {
            LocalAssert.isFalse(dto.isFinalState(), "final state cannot have nextStateId");
            Optional<State> stateOptional = stateAction.findById(dto.getNextStateId());
            LocalAssert.isTrue(stateOptional.isPresent() && batchProductRightService.hasBatchProductEditorRights(stateOptional.get().getBatchProductId(), userId), "State with nextStateId not found");
        }
        if (dto.getLastStateId() > 0) {
            LocalAssert.isFalse(dto.isInitialState(), "initial state cannot have lastStateId");
            Optional<State> stateOptional = stateAction.findById(dto.getLastStateId());
            LocalAssert.isTrue(stateOptional.isPresent() && batchProductRightService.hasBatchProductEditorRights(stateOptional.get().getBatchProductId(), userId), "State with nextStateId not found");
        }

        dto.setId(0);
        State state = MapperUtil.getDozer().map(dto, State.class);
        return configure(stateAction.save(state));
    }

    /**
     * returns {@link StateModel} by id
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @GetMapping("/states")
    public ResponseEntity<?> getById(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(stateAction.existsById(id), APIErrors.OBJECT_NOT_FOUND);
        StateModel stateModel = stateAction.getModelById(id);
        LocalAssert.isTrue(batchProductRightService.hasBatchProductReaderRights(stateModel.getBatchProductId(), userId), APIErrors.BATCH_PRODUCT_NOT_FOUND);
        return configure(stateModel);
    }

    /**
     * Returns the List of {@link StateModel} by {@link BatchProduct#id}
     *
     * @param batchProductId
     * @param servletRequest
     * @return
     */
    @GetMapping("/states/batch-product")
    public ResponseEntity<?> getAllByBatchProductId(@RequestParam int batchProductId, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(batchProductRightService.hasBatchProductReaderRights(batchProductId, userId), APIErrors.BATCH_PRODUCT_NOT_FOUND);
        return configure(stateAction.getAllByBatchProductId(batchProductId));
    }


    /**
     * Check against the links.
     *
     * @param obj
     * @param servletRequest
     * @return
     */
    // TODO: 01/05/2021
    @PutMapping("/state")
    public ResponseEntity<?> update(@Validated @RequestBody Object obj, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        return configure();
    }

    @DeleteMapping("/state")
    public ResponseEntity<?> delete(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        Optional<State> stateOptional = stateAction.findById(id);
        stateOptional.ifPresentOrElse(state -> {
            LocalAssert.isTrue(batchProductRightService.hasBatchProductEditorRights(state.getBatchProductId(), userId), APIErrors.BATCH_PRODUCT_NOT_FOUND);

            stateAction.deleteById(id);
        }, () -> {
            throw new NeesNotFoundException();
        });

        return configure("state deleted successfully");
    }

    /**
     * A {@link State} to be marked complete must have its last State marked as complete unless it is an initial State
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @PutMapping("/state/complete")
    public ResponseEntity<?> markStateComplete(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        Optional<State> stateOptional = stateAction.findById(id);
        stateOptional.ifPresentOrElse(state -> {
            LocalAssert.isTrue(batchProductRightService.hasBatchProductEditorRights(state.getBatchProductId(), userId), APIErrors.BATCH_PRODUCT_NOT_FOUND);
            if (stateAction.existsById(state.getLastStateId())) {
                State lastState = stateAction.getById(state.getLastStateId());
                LocalAssert.isTrue(lastState.isComplete(), String.format("State [ id: %d, name: %s ] must be complete before you can mark this State as complete", lastState.getId(), lastState.getName()));
            }
            if (!state.isInitialState() && !stateAction.existsById(state.getLastStateId())) {
                throw new NeesInfoException("This is a Dangling State [ State which is neither the initial state or has the last State attached to it ] which cannot be marked as complete. ");
            }
            List<Event> eventList = eventAction.getAllByStateId(state.getId());
            eventList.forEach(event -> LocalAssert.isTrue(event.isComplete(), "This state has events that are not complete. Complete all events before marking this State as Complete"));
            state.setComplete(true);
            if (state.isFinalState()) {
                BatchProduct batchProduct = batchProductAction.getById(state.getBatchProductId());
                batchProductAction.save(batchProduct.setStatus(BatchProductStatus.COMPLETE));
            }
        }, () -> {
            throw new NeesNotFoundException();
        });
        return LocalResponse.configure();
    }
}
