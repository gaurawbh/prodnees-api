package com.prodnees.controller;

import com.prodnees.action.stage.StageAction;
import com.prodnees.action.stage.StageReminderAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.StageReminder;
import com.prodnees.dto.stage.StageReminderDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.util.LocalAssert;
import com.prodnees.util.MapperUtil;
import com.prodnees.web.exception.NeesNotFoundException;
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
import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@CrossOrigin
@Transactional
@RequestMapping("/secure/")
public class StageReminderController {
    private final RequestValidator requestValidator;
    private final StageReminderAction stageReminderAction;
    private final StageAction stageAction;

    public StageReminderController(RequestValidator requestValidator,
                                   StageReminderAction stageReminderAction,
                                   StageAction stageAction) {
        this.requestValidator = requestValidator;
        this.stageReminderAction = stageReminderAction;
        this.stageAction = stageAction;
    }

    @PostMapping("/stage-reminder")
    public ResponseEntity<?> save(@Validated @RequestBody StageReminderDto dto) {
        int editorId = requestValidator.extractUserId();
        String sender = requestValidator.extractUserEmail();
        LocalAssert.isTrue(stageAction.hasStageEditorRights(dto.getStageId(), editorId),
                String.format("stage with id: %d not found or you do not have editor right to the batch the stage belongs to.", dto.getStageId()));

        LocalAssert.isTrue(dto.getStageState().equals(StageState.IN_PROGRESS) || dto.getStageState().equals(StageState.COMPLETE),
                String.format("stage reminder can only be set for stage's state %s or %s", StageState.IN_PROGRESS.name(), StageState.COMPLETE.name()));
        for (String email : dto.getRecipientEmails()) {
            LocalAssert.isTrue(requestValidator.isValidEmail(email), String.format("Invalid email format for %s", email));
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
        int userId = requestValidator.extractUserId();
        String userEmail = requestValidator.extractUserEmail();

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
        int userId = requestValidator.extractUserId();
        return configure();
    }

    @DeleteMapping("/stage-reminder")
    public ResponseEntity<?> delete(@RequestParam int id) {
        int userId = requestValidator.extractUserId();
        StageReminder stageReminder = stageReminderAction.findById(id).orElseThrow(NeesNotFoundException::new);
        LocalAssert.isTrue(stageAction.hasStageEditorRights(stageReminder.getStageId(), userId),
                String.format("you do not have enough rights to the stage with id: %d the stage reminder belongs to", stageReminder.getStageId()));
        LocalAssert.isFalse(stageReminder.isSent(), "stage reminder is already sent and cannot be deleted");
        stageReminderAction.deleteById(id);
        return configure("successfully deleted stage reminder");
    }


}
