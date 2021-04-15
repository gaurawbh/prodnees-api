package com.prodnees.action.stage.impl;

import com.prodnees.action.stage.StageReminderAction;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.state.StageReminder;
import com.prodnees.service.email.EmailPlaceHolders;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.stage.StageReminderService;
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
    public void sendStateReminder(StageReminder stageReminder) {
        String[] emails = stageReminder.getRecipients().split(",");
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
