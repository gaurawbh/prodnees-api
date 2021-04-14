package com.prodnees.controller;

import com.prodnees.action.BatchAction;
import com.prodnees.action.state.EventAction;
import com.prodnees.action.state.StateAction;
import com.prodnees.action.state.StateReminderAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.batchproduct.Batch;
import com.prodnees.domain.enums.BatchStatus;
import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.Event;
import com.prodnees.domain.state.State;
import com.prodnees.domain.state.StateReminder;
import com.prodnees.dto.state.StateDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.model.StateModel;
import com.prodnees.service.rels.BatchProductRightService;
import com.prodnees.util.LocalAssert;
import com.prodnees.util.MapperUtil;
import com.prodnees.web.exception.NeesNotFoundException;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class StateController {
    private final RequestValidator requestValidator;
    private final BatchProductRightService batchProductRightService;
    private final EventAction eventAction;
    private final StateAction stateAction;
    private final BatchAction batchAction;
    private final StateReminderAction stateReminderAction;

    public StateController(RequestValidator requestValidator,
                           BatchProductRightService batchProductRightService,
                           EventAction eventAction,
                           StateAction stateAction,
                           BatchAction batchAction,
                           StateReminderAction stateReminderAction) {
        this.requestValidator = requestValidator;
        this.batchProductRightService = batchProductRightService;
        this.eventAction = eventAction;
        this.stateAction = stateAction;
        this.batchAction = batchAction;
        this.stateReminderAction = stateReminderAction;
    }

    /**
     * Rules to Adding new  {@link State} to a {@link Batch}
     * <p>if {@link State} #nextStateId > 0, the {@link State} can not be the finalState</p>
     * <p>if  {@link State} #lastStateId > 0,  the {@link State} must not be the initialState</p>
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
        LocalAssert.isTrue(batchAction.existsByIdAndStatus(dto.getBatchProductId(), BatchStatus.COMPLETE), "you cannot add a State to a Batch Product that is marked as Complete");
        if (dto.getNextStateId() > 0) {
            LocalAssert.isFalse(dto.isFinalState(), "final state cannot have nextStateId");
            Optional<State> stateOptional = stateAction.findById(dto.getNextStateId());
            LocalAssert.isTrue(stateOptional.isPresent() && batchProductRightService.hasBatchProductEditorRights(stateOptional.get().getBatchId(), userId), "State with nextStateId not found");
        }
        if (dto.getLastStateId() > 0) {
            LocalAssert.isFalse(dto.isInitialState(), "initial state cannot have lastStateId");
            Optional<State> stateOptional = stateAction.findById(dto.getLastStateId());
            LocalAssert.isTrue(stateOptional.isPresent() && batchProductRightService.hasBatchProductEditorRights(stateOptional.get().getBatchId(), userId), "State with nextStateId not found");
        }

        dto.setId(0);
        State state = MapperUtil.getDozer().map(dto, State.class).setStatus(StateStatus.OPEN);
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
        LocalAssert.isTrue(stateAction.hasStateReaderRights(id, userId), APIErrors.BATCH_PRODUCT_NOT_FOUND);
        StateModel stateModel = stateAction.getModelById(id);
        return configure(stateModel);
    }

    /**
     * Returns the List of {@link StateModel} by {@link Batch} #id
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
        LocalAssert.isTrue(stateAction.hasStateEditorRights(id, userId), String.format("state not found with id: %d", id));
        stateAction.deleteById(id);
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
            LocalAssert.isTrue(stateAction.hasStateEditorRights(id, userId), APIErrors.BATCH_PRODUCT_NOT_FOUND);
            List<Event> eventList = eventAction.getAllByStateId(state.getId());
            eventList.forEach(event -> LocalAssert.isTrue(event.isComplete(), "This state has events that are not complete. Complete all events before marking this State as Complete"));
            state.setStatus(StateStatus.COMPLETE);
            List<StateReminder> stateReminderList = stateReminderAction.getAllByStateId(id);
            stateReminderList.forEach(stateReminderAction::sendStateReminder);
        }, () -> {
            throw new NeesNotFoundException();
        });
        return LocalResponse.configure();
    }
}
