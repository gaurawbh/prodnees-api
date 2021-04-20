package com.prodnees.controller;

import com.prodnees.action.BatchAction;
import com.prodnees.action.stage.EventAction;
import com.prodnees.action.stage.StageAction;
import com.prodnees.action.stage.StageReminderAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.batch.Batch;
import com.prodnees.domain.enums.BatchState;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.Event;
import com.prodnees.domain.stage.Stage;
import com.prodnees.domain.stage.StageReminder;
import com.prodnees.dto.stage.StageDto;
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
public class StageController {
    private final RequestValidator requestValidator;
    private final BatchRightService batchRightService;
    private final EventAction eventAction;
    private final StageAction stageAction;
    private final BatchAction batchAction;
    private final StageReminderAction stageReminderAction;

    public StageController(RequestValidator requestValidator,
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
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PostMapping("/stage")
    public ResponseEntity<?> save(@Validated @RequestBody StageDto dto,
                                  HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        LocalAssert.isTrue(batchRightService.hasBatchEditorRights(dto.getBatchId(), userId),
                APIErrors.BATCH_NOT_FOUND);
        LocalAssert.isTrue(batchAction.existsByIdAndState(dto.getBatchId(), BatchState.COMPLETE), "you cannot add a Stage to a Batch that is marked as Complete");
        dto.setId(0).
                setIndex(ValidatorUtil.ifValidIntegerOrElse(dto.getIndex(), -1));
        Stage stage = MapperUtil.getDozer().map(dto, Stage.class).setState(StageState.OPEN);
        return configure(stageAction.save(stage));
    }

    /**
     * returns {@link StageModel} by id
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @GetMapping("/stages")
    public ResponseEntity<?> getById(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
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
    @GetMapping("/stages/batch")
    public ResponseEntity<?> getAllByBatchProductId(@RequestParam int batchId, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
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
    @PutMapping("/stage")
    public ResponseEntity<?> update(@Validated @RequestBody StageDto dto,
                                    HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        LocalAssert.isTrue(stageAction.hasStageEditorRights(dto.getId(), userId), String.format("stage with id: %d not found or not enough permission ", dto.getId()));
        Stage stage = stageAction.getById(dto.getId());
        stage.setName(ValidatorUtil.ifValidStringOrElse(dto.getName(), stage.getName()))
                .setDescription(ValidatorUtil.ifValidStringOrElse(dto.getDescription(), stage.getDescription()));

        return configure();
    }

    @DeleteMapping("/stage")
    public ResponseEntity<?> delete(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        LocalAssert.isTrue(stageAction.hasStageEditorRights(id, userId), String.format("stage not found with id: %d", id));
        stageAction.deleteById(id);
        return configure("stage deleted successfully");
    }


    /**
     * A {@link Stage} to be marked complete must have its last State marked as complete unless it is an initial State
     *
     * @param id
     * @param servletRequest
     * @return
     */
    @PutMapping("/stage/start")
    public ResponseEntity<?> markStageStarted(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        Optional<Stage> stageOptional = stageAction.findById(id);
        stageOptional.ifPresentOrElse(stage -> {
            LocalAssert.isTrue(stageAction.hasStageEditorRights(id, userId), APIErrors.BATCH_NOT_FOUND);
            stage.setState(StageState.IN_PROGRESS);
            List<StageReminder> stageReminderList = stageReminderAction.getAllByStageIdAndStageState(id, StageState.IN_PROGRESS);
            stageReminderList.forEach(stageReminderAction::sendStageReminder);
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
    @PutMapping("/stage/complete")
    public ResponseEntity<?> markStageComplete(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        Optional<Stage> stageOptional = stageAction.findById(id);
        stageOptional.ifPresentOrElse(stage -> {
            LocalAssert.isTrue(stageAction.hasStageEditorRights(id, userId), APIErrors.BATCH_NOT_FOUND);
            List<Event> eventList = eventAction.getAllByStageId(stage.getId());
            eventList.forEach(event -> LocalAssert.isTrue(event.isComplete(), "This stage has events that are not complete. Complete all events before marking this State as Complete"));
            stage.setState(StageState.COMPLETE);
            List<StageReminder> stageReminderList = stageReminderAction.getAllByStageIdAndStageState(id, StageState.COMPLETE);
            stageReminderList.forEach(stageReminderAction::sendStageReminder);
        }, () -> {
            throw new NeesNotFoundException();
        });
        return LocalResponse.configure();
    }
}
