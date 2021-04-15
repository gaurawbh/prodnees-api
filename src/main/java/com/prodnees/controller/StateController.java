package com.prodnees.controller;

import com.prodnees.action.BatchAction;
import com.prodnees.action.stage.EventAction;
import com.prodnees.action.stage.StageAction;
import com.prodnees.action.stage.StageReminderAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.BatchState;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.state.Event;
import com.prodnees.domain.state.Stage;
import com.prodnees.domain.state.StageReminder;
import com.prodnees.dto.state.StageDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.model.state.StageModel;
import com.prodnees.service.rels.BatchRightService;
import com.prodnees.util.LocalAssert;
import com.prodnees.util.MapperUtil;
import com.prodnees.util.ValidatorUtil;
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
    private final BatchRightService batchRightService;
    private final EventAction eventAction;
    private final StageAction stageAction;
    private final BatchAction batchAction;
    private final StageReminderAction stageReminderAction;

    public StateController(RequestValidator requestValidator,
                           BatchRightService batchRightService,
                           EventAction eventAction,
                           StageAction stageAction,
                           BatchAction batchAction,
                           StageReminderAction stageReminderAction) {
        this.requestValidator = requestValidator;
        this.batchRightService = batchRightService;
        this.eventAction = eventAction;
        this.stageAction = stageAction;
        this.batchAction = batchAction;
        this.stageReminderAction = stageReminderAction;
    }

    /**
     * Rules to Adding new  {@link Stage} to a {@link Batch}
     * <p>if {@link Stage} #nextStateId > 0, the {@link Stage} can not be the finalState</p>
     * <p>if  {@link Stage} #lastStateId > 0,  the {@link Stage} must not be the initialState</p>
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PostMapping("/state")
    public ResponseEntity<?> save(@Validated @RequestBody StageDto dto,
                                  HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(batchRightService.hasBatchEditorRights(dto.getBatchId(), userId),
                APIErrors.BATCH_NOT_FOUND);
        LocalAssert.isTrue(batchAction.existsByIdAndState(dto.getBatchId(), BatchState.COMPLETE), "you cannot add a State to a Batch Product that is marked as Complete");
        dto.setId(0).
                setIndex(ValidatorUtil.ifValidIntegerOrElse(dto.getIndex(), -1));
        Stage stage = MapperUtil.getDozer().map(dto, Stage.class).setStatus(StageState.OPEN);
        return configure(stageAction.save(stage));
    }

    /**
     * returns {@link StageModel} by id
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @GetMapping("/states")
    public ResponseEntity<?> getById(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(stageAction.hasStageReaderRights(id, userId), APIErrors.BATCH_NOT_FOUND);
        StageModel stageModel = stageAction.getModelById(id);
        return configure(stageModel);
    }

    /**
     * Returns the List of {@link StageModel} by {@link Batch} #id
     *
     * @param batchId
     * @param servletRequest
     * @return
     */
    @GetMapping("/states/batch")
    public ResponseEntity<?> getAllByBatchProductId(@RequestParam int batchId, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(batchRightService.hasBatchReaderRights(batchId, userId), APIErrors.BATCH_NOT_FOUND);
        return configure(stageAction.getAllByBatchId(batchId));
    }

    /**
     * Check against the links.
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PutMapping("/state")
    public ResponseEntity<?> update(@Validated @RequestBody StageDto dto,
                                    HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(stageAction.hasStageEditorRights(dto.getId(), userId), String.format("state with id: %d not found or not enough permission ", dto.getId()));
        Stage stage = stageAction.getById(dto.getId());
        stage.setName(ValidatorUtil.ifValidStringOrElse(dto.getName(), stage.getName()))
                .setDescription(ValidatorUtil.ifValidStringOrElse(dto.getDescription(), stage.getDescription()));

        return configure();
    }

    @DeleteMapping("/state")
    public ResponseEntity<?> delete(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(stageAction.hasStageEditorRights(id, userId), String.format("state not found with id: %d", id));
        stageAction.deleteById(id);
        return configure("state deleted successfully");
    }


    /**
     * A {@link Stage} to be marked complete must have its last State marked as complete unless it is an initial State
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @PutMapping("/state/start")
    public ResponseEntity<?> markStateStarted(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        Optional<Stage> stateOptional = stageAction.findById(id);
        stateOptional.ifPresentOrElse(state -> {
            LocalAssert.isTrue(stageAction.hasStageEditorRights(id, userId), APIErrors.BATCH_NOT_FOUND);
            state.setStatus(StageState.IN_PROGRESS);
            List<StageReminder> stageReminderList = stageReminderAction.getAllByStageIdAndStageState(id, StageState.IN_PROGRESS);
            stageReminderList.forEach(stageReminderAction::sendStateReminder);
        }, () -> {
            throw new NeesNotFoundException();
        });
        return LocalResponse.configure();
    }

    /**
     * A {@link Stage} to be marked complete must have its last State marked as complete unless it is an initial State
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @PutMapping("/state/complete")
    public ResponseEntity<?> markStateComplete(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        Optional<Stage> stateOptional = stageAction.findById(id);
        stateOptional.ifPresentOrElse(state -> {
            LocalAssert.isTrue(stageAction.hasStageEditorRights(id, userId), APIErrors.BATCH_NOT_FOUND);
            List<Event> eventList = eventAction.getAllByStageId(state.getId());
            eventList.forEach(event -> LocalAssert.isTrue(event.isComplete(), "This state has events that are not complete. Complete all events before marking this State as Complete"));
            state.setStatus(StageState.COMPLETE);
            List<StageReminder> stageReminderList = stageReminderAction.getAllByStageIdAndStageState(id, StageState.COMPLETE);
            stageReminderList.forEach(stageReminderAction::sendStateReminder);
        }, () -> {
            throw new NeesNotFoundException();
        });
        return LocalResponse.configure();
    }
}
