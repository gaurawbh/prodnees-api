package com.prodnees.core.action.stage.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.stage.StageReminderAction;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.StageReminder;
import com.prodnees.core.dto.stage.StageReminderDto;
import com.prodnees.core.service.email.EmailPlaceHolders;
import com.prodnees.core.service.email.LocalEmailService;
import com.prodnees.core.service.stage.StageReminderService;
import com.prodnees.core.util.MapperUtil;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StageReminderActionImpl implements StageReminderAction {
    private final StageReminderService stageReminderService;
    private final LocalEmailService localEmailService;
    Logger localLogger = LoggerFactory.getLogger(this.getClass());


    public StageReminderActionImpl(StageReminderService stageReminderService,
                                   LocalEmailService localEmailService) {
        this.stageReminderService = stageReminderService;
        this.localEmailService = localEmailService;
    }

    @Override
    public Optional<StageReminder> findById(int id) {
        return stageReminderService.findById(id);
    }

    @Override
    public StageReminder save(StageReminder stageReminder) {
        return stageReminderService.save(stageReminder);
    }

    @Override
    public StageReminder addNew(StageReminderDto dto) {
        String sender = RequestContext.getUsername();

        StageReminder stageReminder = MapperUtil.getDozer().map(dto, StageReminder.class);
        stageReminder.setRecipientsJson(String.join(", ", dto.getRecipientEmails()))
                .setSender(sender)
                .setSent(false);
        return stageReminderService.save(stageReminder);
    }

    @Override
    public StageReminder getById(int id) {
        return stageReminderService.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("Stage Reminder with id: %d not found", id)));
    }

    @Override
    public List<StageReminder> getAllByStageId(int stageId) {
        return stageReminderService.getAllByStageId(stageId);
    }

    @Override
    public List<StageReminder> getAllBySender(String senderEmail) {
        return stageReminderService.getAllBySender(senderEmail);
    }

    @Override
    public List<StageReminder> getAllByStageIdAndStageState(int stageId, StageState stageState) {
        return stageReminderService.getAllByStageIdAndStateState(stageId, stageState);
    }

    @Override
    public void deleteById(int id) {
        stageReminderService.deleteById(id);

    }

    @Override
    public void deleteAllByStageId(int stageId) {
        stageReminderService.deleteAllByStageId(stageId);
    }

    @Override
    public void sendStageReminder(StageReminder stageReminder) {
        String[] emails = stageReminder.getRecipientsJson().split(",");
        for (String email : emails) {
            Map<String, Object> stateReminderMail = new HashMap<>();
            stateReminderMail.put(EmailPlaceHolders.TITLE, "You have a new State Reminder");
            stateReminderMail.put(EmailPlaceHolders.MESSAGE, stageReminder.getMessage());
            stateReminderMail.put(EmailPlaceHolders.PARA_ONE, String.format("Reminder sent by %s", stageReminder.getSender()));
            try {
                localEmailService.sendTemplateEmail(email, "New State Reminder", stateReminderMail);
            } catch (MessagingException | UnsupportedEncodingException e) {
                localLogger.warn("state reminder with id: {} for stateId: {} could not be sent", stageReminder.getId(), stageReminder.getStageId());
                e.printStackTrace();
            }

        }


    }
}
