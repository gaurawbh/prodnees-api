package com.prodnees.controller;

import com.prodnees.action.rel.BatchProductRightAction;
import com.prodnees.action.state.StateAction;
import com.prodnees.action.state.StateReminderAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.StateReminder;
import com.prodnees.dto.state.StateReminderDto;
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
    private final StateReminderAction stateReminderAction;
    private final StateAction stateAction;
    private final BatchProductRightAction batchProductRightAction;

    public StateReminderController(RequestValidator requestValidator,
                                   StateReminderAction stateReminderAction,
                                   StateAction stateAction,
                                   BatchProductRightAction batchProductRightAction) {
        this.requestValidator = requestValidator;
        this.stateReminderAction = stateReminderAction;
        this.stateAction = stateAction;
        this.batchProductRightAction = batchProductRightAction;
    }

    @PostMapping("/state-reminder")
    public ResponseEntity<?> save(@Validated @RequestBody StateReminderDto dto,
                                  HttpServletRequest servletRequest) {
        int editorId = requestValidator.extractUserId(servletRequest);
        String sender = requestValidator.extractUserEmail(servletRequest);
        LocalAssert.isTrue(stateAction.hasStateEditorRights(dto.getStateId(), editorId),
                String.format("state with id: %d not found or you do not have editor right to the batch product the state belongs to.", dto.getStateId()));

        LocalAssert.isTrue(dto.getStateStatus().equals(StateStatus.STARTED) || dto.getStateStatus().equals(StateStatus.COMPLETE),
                String.format("state reminder can only be set for state status %s or %s", StateStatus.STARTED.name(), StateStatus.COMPLETE.name()));
        for (String email : dto.getRecipientEmails()) {
            LocalAssert.isTrue(requestValidator.isValidEmail(email), String.format("Invalid email format for %s", email));
        }
        StateReminder stateReminder = MapperUtil.getDozer().map(dto, StateReminder.class);
        stateReminder.setRecipients(String.join(", ", dto.getRecipientEmails()))
                .setSender(sender)
                .setSent(false);
        return configure(stateReminderAction.save(stateReminder));
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
            Optional<StateReminder> stateReminderOptional = stateReminderAction.findById(id.get());
            stateReminderOptional.ifPresentOrElse(stateReminder -> {
                LocalAssert.isTrue(stateAction.hasStateReaderRights(stateReminder.getStateId(), userId), APIErrors.OBJECT_NOT_FOUND);
                atomicReference.set(stateReminder);
            }, () -> {
                throw new NeesNotFoundException();
            });
            return configure(atomicReference.get());
        } else if (stateId.isPresent()) {
            LocalAssert.isTrue(stateAction.hasStateReaderRights(stateId.get(), userId), APIErrors.OBJECT_NOT_FOUND);
            atomicReference.set(stateReminderAction.getAllByStateId(stateId.get()));
            return configure(atomicReference.get());
        } else {
            return configure(stateReminderAction.getAllBySender(userEmail));
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
        Optional<StateReminder> stateReminderOptional = stateReminderAction.findById(id);
        stateReminderOptional.ifPresentOrElse(stateReminder -> {
            LocalAssert.isTrue(stateAction.hasStateEditorRights(stateReminder.getStateId(), userId),
                    String.format("you do not have enough rights to the state with id: %d the state reminder belongs to", stateReminder.getStateId()));
            LocalAssert.isFalse(stateReminder.isSent(), "state reminder is already sent and cannot be deleted");
            stateReminderAction.deleteById(id);
        }, () -> {
            throw new NeesNotFoundException();
        });
        return configure("successfully deleted state reminder");
    }


}
