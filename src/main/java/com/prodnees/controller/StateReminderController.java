package com.prodnees.controller;

import com.prodnees.action.rel.BatchRightAction;
import com.prodnees.action.stage.StageAction;
import com.prodnees.action.stage.StageReminderAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.state.StageReminder;
import com.prodnees.dto.state.StageReminderDto;
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
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@CrossOrigin
@Transactional
@RequestMapping("/secure/")
public class StateReminderController {
    private final RequestValidator requestValidator;
    private final StageReminderAction stageReminderAction;
    private final StageAction stageAction;
    private final BatchRightAction batchRightAction;

    public StateReminderController(RequestValidator requestValidator,
                                   StageReminderAction stageReminderAction,
                                   StageAction stageAction,
                                   BatchRightAction batchRightAction) {
        this.requestValidator = requestValidator;
        this.stageReminderAction = stageReminderAction;
        this.stageAction = stageAction;
        this.batchRightAction = batchRightAction;
    }

    @PostMapping("/state-reminder")
    public ResponseEntity<?> save(@Validated @RequestBody StageReminderDto dto,
                                  HttpServletRequest servletRequest) {
        int editorId = requestValidator.extractUserId(servletRequest);
        String sender = requestValidator.extractUserEmail(servletRequest);
        LocalAssert.isTrue(stageAction.hasStageEditorRights(dto.getStateId(), editorId),
                String.format("state with id: %d not found or you do not have editor right to the batch product the state belongs to.", dto.getStateId()));

        LocalAssert.isTrue(dto.getStateStatus().equals(StageState.IN_PROGRESS) || dto.getStateStatus().equals(StageState.COMPLETE),
                String.format("state reminder can only be set for state status %s or %s", StageState.IN_PROGRESS.name(), StageState.COMPLETE.name()));
        for (String email : dto.getRecipientEmails()) {
            LocalAssert.isTrue(requestValidator.isValidEmail(email), String.format("Invalid email format for %s", email));
        }
        StageReminder stageReminder = MapperUtil.getDozer().map(dto, StageReminder.class);
        stageReminder.setRecipients(String.join(", ", dto.getRecipientEmails()))
                .setSender(sender)
                .setSent(false);
        return configure(stageReminderAction.save(stageReminder));
    }

    //todo for batchProductId
    @GetMapping("/state-reminders")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 @RequestParam Optional<Integer> stateId,
                                 @RequestParam Optional<Integer> batchProductId,
                                 HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        String userEmail = requestValidator.extractUserEmail(servletRequest);

        AtomicReference<Object> atomicReference = new AtomicReference<>();

        if (id.isPresent()) {
            Optional<StageReminder> stateReminderOptional = stageReminderAction.findById(id.get());
            stateReminderOptional.ifPresentOrElse(stateReminder -> {
                LocalAssert.isTrue(stageAction.hasStageReaderRights(stateReminder.getStageId(), userId), APIErrors.OBJECT_NOT_FOUND);
                atomicReference.set(stateReminder);
            }, () -> {
                throw new NeesNotFoundException();
            });
            return configure(atomicReference.get());
        } else if (stateId.isPresent()) {
            LocalAssert.isTrue(stageAction.hasStageReaderRights(stateId.get(), userId), APIErrors.OBJECT_NOT_FOUND);
            atomicReference.set(stageReminderAction.getAllByStageId(stateId.get()));
            return configure(atomicReference.get());
        } else {
            return configure(stageReminderAction.getAllBySender(userEmail));
        }
    }

    @PutMapping("/state-reminder")
    public ResponseEntity<?> update(@Validated @RequestBody Object obj,
                                    HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        return configure();
    }

    @DeleteMapping("/state-reminder")
    public ResponseEntity<?> delete(@RequestParam int id,
                                    HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        Optional<StageReminder> stateReminderOptional = stageReminderAction.findById(id);
        stateReminderOptional.ifPresentOrElse(stateReminder -> {
            LocalAssert.isTrue(stageAction.hasStageEditorRights(stateReminder.getStageId(), userId),
                    String.format("you do not have enough rights to the state with id: %d the state reminder belongs to", stateReminder.getStageId()));
            LocalAssert.isFalse(stateReminder.isSent(), "state reminder is already sent and cannot be deleted");
            stageReminderAction.deleteById(id);
        }, () -> {
            throw new NeesNotFoundException();
        });
        return configure("successfully deleted state reminder");
    }


}
