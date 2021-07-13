package com.prodnees.core.controller;

import com.prodnees.core.action.BatchAction;
import com.prodnees.core.action.stage.StageAction;
import com.prodnees.core.action.stage.StageReminderAction;
import com.prodnees.core.action.stage.StageTodoAction;
import com.prodnees.core.domain.batch.Batch;
import com.prodnees.core.domain.enums.BatchState;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.Stage;
import com.prodnees.core.domain.stage.StageReminder;
import com.prodnees.core.domain.stage.StageTodo;
import com.prodnees.core.dto.stage.StageDto;
import com.prodnees.core.model.stage.StageModel;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.ValidatorUtil;
import com.prodnees.core.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.prodnees.core.web.response.LocalResponse.configure;

@RestController
@Transactional
public class StageController {
    private final StageTodoAction stageTodoAction;
    private final StageAction stageAction;
    private final BatchAction batchAction;
    private final StageReminderAction stageReminderAction;

    public StageController(StageTodoAction stageTodoAction,
                           StageAction stageAction,
                           BatchAction batchAction,
                           StageReminderAction stageReminderAction) {
        this.stageTodoAction = stageTodoAction;
        this.stageAction = stageAction;
        this.batchAction = batchAction;
        this.stageReminderAction = stageReminderAction;
    }

    /**
     * Rules to Adding new  {@link Stage} to a {@link Batch}
     *
     * @param dto
     * @return
     */
    @PostMapping("/stage")
    public ResponseEntity<?> save(@Validated @RequestBody StageDto dto) {
        Batch batch = batchAction.getById(dto.getBatchId());
        LocalAssert.isFalse(batch.getState().equals(BatchState.COMPLETE),
                "you cannot add a Stage to a Batch that is marked as Complete");

        return configure(stageAction.addNew(dto));
    }

    /**
     * returns {@link StageModel} by id
     *
     * @param id
     * @return
     */
    @GetMapping("/stages/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        StageModel stageModel = stageAction.getModelById(id);
        return configure(stageModel);
    }

    /**
     * Returns the List of {@link StageModel} by {@link Batch} #id
     *
     * @param batchId
     * @return
     */
    @GetMapping("/stages/batch/{batchId}")
    public ResponseEntity<?> getAllByBatchId(@PathVariable int batchId) {
        return configure(stageAction.getAllByBatchId(batchId));
    }

    /**
     * Check against the links.
     *
     * @param dto
     * @return
     */
    @PutMapping("/stage")
    public ResponseEntity<?> update(@Validated @RequestBody StageDto dto) {
        Stage stage = stageAction.getById(dto.getId());
        stage.setDescription(ValidatorUtil.ifValidStringOrElse(dto.getDescription(), stage.getDescription()));

        return configure();
    }

    @DeleteMapping("/stage/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        stageAction.deleteById(id);
        return configure("stage deleted successfully");
    }


    /**
     * A {@link Stage} to be marked complete must have its last State marked as complete unless it is an initial State
     *
     * @param id
     * @return
     */
    @PutMapping("/stage/{id}/start")
    public ResponseEntity<?> markStageStarted(@PathVariable int id) {
        Stage stage = stageAction.getById(id);
        stage.setState(StageState.IN_PROGRESS);
        List<StageReminder> stageReminderList = stageReminderAction.getAllByStageIdAndStageState(id, StageState.IN_PROGRESS);
        stageReminderList.forEach(stageReminderAction::sendStageReminder);

        return configure();
    }

    /**
     * A {@link Stage} to be marked complete must have its last State marked as complete unless it is an initial State
     *
     * @param id
     * @return
     */
    @PutMapping("/stage/complete/{id}")
    public ResponseEntity<?> markStageComplete(@PathVariable int id) {
        Stage stage = stageAction.getById(id);

        List<StageTodo> stageTodoList = stageTodoAction.getAllByStageId(stage.getId());
        stageTodoList.forEach(stageTodo -> LocalAssert.isTrue(stageTodo.isComplete(), "This stage has events that are not complete. Complete all events before marking this State as Complete"));
        stage.setState(StageState.COMPLETE);
        List<StageReminder> stageReminderList = stageReminderAction.getAllByStageIdAndStageState(id, StageState.COMPLETE);
        stageReminderList.forEach(stageReminderAction::sendStageReminder);

        return LocalResponse.configure();
    }
}
