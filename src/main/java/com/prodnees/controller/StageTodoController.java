package com.prodnees.controller;

import com.prodnees.action.rel.BatchRightAction;
import com.prodnees.action.stage.StageAction;
import com.prodnees.action.stage.StageTodoAction;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.Stage;
import com.prodnees.domain.stage.StageTodo;
import com.prodnees.dto.stage.StageTodoDto;
import com.prodnees.filter.RequestValidator;
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
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
//@RequestMapping("/secure")
@CrossOrigin
@Transactional
public class StageTodoController {

    private final RequestValidator requestValidator;
    private final StageTodoAction stageTodoAction;
    private final BatchRightAction batchRightAction;
    private final StageAction stageAction;

    public StageTodoController(RequestValidator requestValidator,
                               StageTodoAction stageTodoAction,
                               BatchRightAction batchRightAction,
                               StageAction stageAction) {
        this.requestValidator = requestValidator;
        this.stageTodoAction = stageTodoAction;
        this.batchRightAction = batchRightAction;
        this.stageAction = stageAction;
    }

    @PostMapping("/stage-todo")
    public ResponseEntity<?> save(@Validated @RequestBody StageTodoDto dto) {
        Stage stage = stageAction.findById(dto.getStageId()).orElseThrow(NeesNotFoundException::new);
        int editorId = requestValidator.extractUserId();
        LocalAssert.isTrue(batchRightAction.hasBatchEditorRights(stage.getBatchId(), editorId),
                "batch the stage belongs to not found or insufficient rights to add Stage Todo to the Stage");
        StageTodo stageTodo = MapperUtil.getDozer().map(dto, StageTodo.class)
                .setBatchId(stage.getBatchId())
                .setComplete(false);
        return LocalResponse.configure(stageTodoAction.save(stageTodo));
    }

    @GetMapping("/stage-todos")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
        }, () -> {
        });
        return LocalResponse.configure();
    }

    @PutMapping("/stage-todo")
    public ResponseEntity<?> update(@Validated @RequestBody StageTodoDto dto) {
        Stage stage = stageAction.findById(dto.getStageId()).orElseThrow(NeesNotFoundException::new);
        int editorId = requestValidator.extractUserId();
        LocalAssert.isFalse(stage.getState().equals(StageState.COMPLETE),
                "you cannot edit contents of a completed Stage. Mark Stage incomplete and try this operation again");
        LocalAssert.isTrue(batchRightAction.hasBatchEditorRights(stage.getBatchId(), editorId),
                "batch the stage belongs to not found or insufficient rights to add Stage Todo to the Stage");
        return LocalResponse.configure();
    }

    @DeleteMapping("/stage-todo")
    public ResponseEntity<?> delete(@RequestParam int id,
                                    HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId();
        return LocalResponse.configure();
    }


}
