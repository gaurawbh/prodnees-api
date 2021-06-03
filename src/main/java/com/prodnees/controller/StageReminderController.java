package com.prodnees.controller;

import com.prodnees.action.stage.StageAction;
import com.prodnees.action.stage.StageReminderAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.StageReminder;
import com.prodnees.dto.stage.StageReminderDto;
import com.prodnees.filter.RequestContext;
import com.prodnees.util.LocalAssert;
import com.prodnees.util.MapperUtil;
import com.prodnees.web.exception.NeesNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.prodnees.web.response.LocalResponse.configure;

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

    @PostMapping("/stage-reminder")
    public ResponseEntity<?> save(@Validated @RequestBody StageReminderDto dto) {
        int editorId = RequestContext.getUserId();
        String sender = RequestContext.getUsername();
        LocalAssert.isTrue(stageAction.hasStageEditorRights(dto.getStageId(), editorId),
                String.format("stage with id: %d not found or you do not have editor right to the batch the stage belongs to.", dto.getStageId()));

        LocalAssert.isTrue(dto.getStageState().equals(StageState.IN_PROGRESS) || dto.getStageState().equals(StageState.COMPLETE),
                String.format("stage reminder can only be set for stage's state %s or %s", StageState.IN_PROGRESS.name(), StageState.COMPLETE.name()));
        for (String email : dto.getRecipientEmails()) {
            LocalAssert.isTrue(RequestContext.isValidEmail(email), String.format("Invalid email format for %s", email));
        }
        StageReminder stageReminder = MapperUtil.getDozer().map(dto, StageReminder.class);
        stageReminder.setRecipients(String.join(", ", dto.getRecipientEmails()))
                .setSender(sender)
                .setSent(false);
        return configure(stageReminderAction.save(stageReminder));
    }

    //todo for batchId
    @GetMapping("/stage-reminders")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 @RequestParam Optional<Integer> stageId,
                                 @RequestParam Optional<Integer> batchId) {
        int userId = RequestContext.getUserId();
        String userEmail = RequestContext.getUsername();

        if (id.isPresent()) {
            StageReminder stageReminder = stageReminderAction.findById(id.get()).orElseThrow(NeesNotFoundException::new);
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
        StageReminder stageReminder = stageReminderAction.findById(id).orElseThrow(NeesNotFoundException::new);
        LocalAssert.isTrue(stageAction.hasStageEditorRights(stageReminder.getStageId(), userId),
                String.format("you do not have enough rights to the stage with id: %d the stage reminder belongs to", stageReminder.getStageId()));
        LocalAssert.isFalse(stageReminder.isSent(), "stage reminder is already sent and cannot be deleted");
        stageReminderAction.deleteById(id);
        return configure("successfully deleted stage reminder");
    }


}
