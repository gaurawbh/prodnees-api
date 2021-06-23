package com.prodnees.core.controller;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.stage.StageAction;
import com.prodnees.core.action.stage.StageReminderAction;
import com.prodnees.core.config.constants.APIErrors;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.Stage;
import com.prodnees.core.domain.stage.StageReminder;
import com.prodnees.core.dto.stage.StageReminderDto;
import com.prodnees.core.util.LocalAssert;
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

import java.util.Optional;

import static com.prodnees.core.web.response.LocalResponse.configure;

@RestController
@CrossOrigin
@Transactional
@RequestMapping("/secure/")
public class StageReminderController {
    private final StageReminderAction stageReminderAction;
    private final StageAction stageAction;

    public StageReminderController(StageReminderAction stageReminderAction,
                                   StageAction stageAction) {
        this.stageReminderAction = stageReminderAction;
        this.stageAction = stageAction;
    }

    /**
     * {@link StageReminder} can be added to a {@link Stage} in {@link StageState#IN_PROGRESS} or in {@link StageState#COMPLETE}
     * <i>Meaning, when to send this reminder? When the {@link StageState} moves to {@link StageState#IN_PROGRESS} or when it's marked as {@link StageState#COMPLETE} ? </i>
     * <p>{@link StageReminder} cannot be added to a {@link Stage} that is already marked as {@link StageState#COMPLETE}</p>
     *
     * @param reminderDto
     * @return
     */
    @PostMapping("/stage-reminder")
    public ResponseEntity<?> save(@Validated @RequestBody StageReminderDto reminderDto) {
        int editorId = RequestContext.getUserId();
        LocalAssert.isTrue(stageAction.hasStageEditorRights(reminderDto.getStageId(), editorId),
                String.format("stage with id: %d not found or you do not have editor right to the batch the stage belongs to.", reminderDto.getStageId()));

        LocalAssert.isTrue(reminderDto.getStageState().equals(StageState.IN_PROGRESS) || reminderDto.getStageState().equals(StageState.COMPLETE),
                String.format("stage reminder can only be set for stage's state %s or %s", StageState.IN_PROGRESS.name(), StageState.COMPLETE.name()));

        Stage stage = stageAction.getById(reminderDto.getStageId());
        LocalAssert.isFalse(stage.getState().equals(StageState.COMPLETE),
                String.format("Stage with id: %d is already marked as complete. You cannot add reminders to a Stage marked as complete", reminderDto.getStageId()));
        if (reminderDto.getStageState().equals(StageState.IN_PROGRESS)) {
            LocalAssert.isTrue(stage.getState().equals(StageState.OPEN), String.format("Stage with id: %d is already %s", reminderDto.getStageId(), stage.getState()));
        }
        for (String email : reminderDto.getRecipientEmails()) {
            LocalAssert.isTrue(RequestContext.isValidEmail(email), String.format("Invalid email format for %s", email));
        }

        return configure(stageReminderAction.addNew(reminderDto));
    }

    @GetMapping("/stage-reminders")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 @RequestParam Optional<Integer> stageId) {
        int userId = RequestContext.getUserId();
        String userEmail = RequestContext.getUsername();

        if (id.isPresent()) {
            StageReminder stageReminder = stageReminderAction.getById(id.get());
            LocalAssert.isTrue(stageAction.hasStageReaderRights(stageReminder.getStageId(), userId), APIErrors.OBJECT_NOT_FOUND);
            return configure(stageReminder);
        } else if (stageId.isPresent()) {
            LocalAssert.isTrue(stageAction.hasStageReaderRights(stageId.get(), userId), APIErrors.OBJECT_NOT_FOUND);
            return configure(stageReminderAction.getAllByStageId(stageId.get()));
        } else {
            return configure(stageReminderAction.getAllBySender(userEmail));
        }
    }

    @PutMapping("/stage-reminder")
    public ResponseEntity<?> update(@Validated @RequestBody Object obj) {
        int userId = RequestContext.getUserId();
        return configure();
    }

    @DeleteMapping("/stage-reminder")
    public ResponseEntity<?> delete(@RequestParam int id) {
        int userId = RequestContext.getUserId();
        StageReminder stageReminder = stageReminderAction.getById(id);
        LocalAssert.isTrue(stageAction.hasStageEditorRights(stageReminder.getStageId(), userId),
                String.format("you do not have enough rights to the stage with id: %d the stage reminder belongs to", stageReminder.getStageId()));
        LocalAssert.isFalse(stageReminder.isSent(), "stage reminder is already sent and cannot be deleted");
        stageReminderAction.deleteById(id);
        return configure("successfully deleted stage reminder with id: " + id);
    }


}
