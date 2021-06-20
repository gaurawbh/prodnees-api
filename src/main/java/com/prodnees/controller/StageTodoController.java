package com.prodnees.controller;

import com.prodnees.action.rel.BatchRightAction;
import com.prodnees.action.stage.StageAction;
import com.prodnees.action.stage.StageTodoAction;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.Stage;
import com.prodnees.domain.stage.StageTodo;
import com.prodnees.dto.stage.StageTodoDto;
import com.prodnees.util.LocalAssert;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure")
@CrossOrigin
@Transactional
public class StageTodoController {

    private final StageTodoAction stageTodoAction;
    private final BatchRightAction batchRightAction;
    private final StageAction stageAction;

    public StageTodoController(StageTodoAction stageTodoAction,
                               BatchRightAction batchRightAction,
                               StageAction stageAction) {
        this.stageTodoAction = stageTodoAction;
        this.batchRightAction = batchRightAction;
        this.stageAction = stageAction;
    }

    @PostMapping("/stage-todo")
    public ResponseEntity<?> save(@Validated @RequestBody StageTodoDto dto) {
        Stage stage = stageAction.getById(dto.getStageId());
        int editorId = RequestContext.getUserId();
        LocalAssert.isTrue(batchRightAction.hasBatchEditorRights(stage.getBatchId(), editorId),
                "batch the stage belongs to not found or insufficient rights to add Stage Todo to the Stage");
        return configure(stageTodoAction.addNew(dto, stage.getBatchId()));
    }

    @GetMapping("/stage-todos")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 @RequestParam Optional<Integer> stageId) {
        LocalAssert.isTrue(id.isPresent() || stageId.isPresent(), "either id or stageId must be present");
        int readerId = RequestContext.getUserId();
        if (id.isPresent()) {
            StageTodo stageTodo = stageTodoAction.getById(id.get());
            Stage stage = stageAction.getById(stageTodo.getStageId());
            LocalAssert.isTrue(batchRightAction.hasBatchReaderRights(stage.getBatchId(), readerId),
                    String.format("batch the stage belongs to not found or insufficient rights to view this Stage Todo with id %d", id.get()));
            return configure(stageTodo);
        } else {
            Stage stage = stageAction.getById(stageId.get());
            LocalAssert.isTrue(batchRightAction.hasBatchReaderRights(stage.getBatchId(), readerId),
                    String.format("stage with stageId: %d not found or insufficient rights to view this Stage Todo.", stageId.get()));
            return configure(stageTodoAction.getAllByStageId(stageId.get()));
        }
    }

    @PutMapping("/stage-todo")
    public ResponseEntity<?> update(@Validated @RequestBody StageTodoDto dto) {
        Stage stage = stageAction.getById(dto.getStageId());
        int editorId = RequestContext.getUserId();
        LocalAssert.isFalse(stage.getState().equals(StageState.COMPLETE),
                "you cannot edit contents of a completed Stage. Mark Stage incomplete and try this operation again");
        LocalAssert.isTrue(batchRightAction.hasBatchEditorRights(stage.getBatchId(), editorId),
                "batch the stage belongs to not found or insufficient rights to add Stage Todo to the Stage");
        return configure();
    }

    @DeleteMapping("/stage-todo")
    public ResponseEntity<?> delete(@RequestParam int id) {
        StageTodo stageTodo = stageTodoAction.getById(id);
        int editorId = RequestContext.getUserId();
        LocalAssert.isTrue(stageAction.hasStageEditorRights(stageTodo.getStageId(), editorId),
                String.format("Stage Todo with id: %d not found or insufficient rights to delete this Stage Todo.", id));
        stageTodoAction.deleteById(id);
        return configure("successfully deleted Stage Todo with id: " + id);
    }


}
