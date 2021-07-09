package com.prodnees.core.controller;

import com.prodnees.core.action.stage.StageAction;
import com.prodnees.core.action.stage.StageTodoAction;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.Stage;
import com.prodnees.core.domain.stage.StageTodo;
import com.prodnees.core.dto.stage.StageTodoDto;
import com.prodnees.core.util.LocalAssert;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.prodnees.core.web.response.LocalResponse.configure;

@RestController
@CrossOrigin
@Transactional
public class StageTodoController {

    private final StageTodoAction stageTodoAction;
    private final StageAction stageAction;

    public StageTodoController(StageTodoAction stageTodoAction,
                               StageAction stageAction) {
        this.stageTodoAction = stageTodoAction;
        this.stageAction = stageAction;
    }

    @PostMapping("/stage-todo")
    public ResponseEntity<?> save(@Validated @RequestBody StageTodoDto dto) {
        Stage stage = stageAction.getById(dto.getStageId());
        return configure(stageTodoAction.addNew(dto, stage.getBatchId()));
    }

    @GetMapping("/stage-todos")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 @RequestParam Optional<Integer> stageId) {
        LocalAssert.isTrue(id.isPresent() || stageId.isPresent(), "either id or stageId must be present");
        if (id.isPresent()) {
            StageTodo stageTodo = stageTodoAction.getById(id.get());
            return configure(stageTodo);
        } else {
            return configure(stageTodoAction.getAllByStageId(stageId.get()));
        }
    }

    //todo incorrect behaviour
    @PutMapping("/stage-todo")
    public ResponseEntity<?> update(@Validated @RequestBody StageTodoDto dto) {
        Stage stage = stageAction.getById(dto.getStageId());
        LocalAssert.isFalse(stage.getState().equals(StageState.COMPLETE),
                "you cannot edit contents of a completed Stage. Mark Stage incomplete and try this operation again");
        return configure();
    }

    //todo incomplete
    @PutMapping("/stage-todo/complete")
    public ResponseEntity<?> markComplete(@RequestParam int id) {
        throw new NotImplementedException(" This end point is not yet implemented");

    }

    @DeleteMapping("/stage-todo")
    public ResponseEntity<?> delete(@RequestParam int id) {
        stageTodoAction.deleteById(id);
        return configure("successfully deleted Stage Todo with id: " + id);
    }


}
