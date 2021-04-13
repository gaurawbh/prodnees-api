package com.prodnees.action.state.impl;

import com.prodnees.action.state.StateReminderAction;
import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.StateReminder;
import com.prodnees.service.email.EmailPlaceHolders;
import com.prodnees.service.email.LocalEmailService;
import com.prodnees.service.state.StateReminderService;
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
public class StateReminderActionImpl implements StateReminderAction {
    private final StateReminderService stateReminderService;
    private final LocalEmailService localEmailService;
    Logger localLogger = LoggerFactory.getLogger(this.getClass());


    public StateReminderActionImpl(StateReminderService stateReminderService,
                                   LocalEmailService localEmailService) {
        this.stateReminderService = stateReminderService;
        this.localEmailService = localEmailService;
    }

    @Override
    public Optional<StateReminder> findById(int id) {
        return stateReminderService.findById(id);
    }

    @Override
    public StateReminder save(StateReminder stateReminder) {
        return stateReminderService.save(stateReminder);
    }

    @Override
    public List<StateReminder> getAllByStateId(int stateId) {
        return stateReminderService.getAllByStateId(stateId);
    }

    @Override
    public List<StateReminder> getAllByStateIdAndStateStatus(int stateId, StateStatus stateStatus) {
        return stateReminderService.getAllByStateIdAndStateStatus(stateId, stateStatus);
    }

    @Override
    public void deleteById(int id) {
        stateReminderService.deleteById(id);

    }

    @Override
    public void deleteAllByStateId(int stateId) {
        stateReminderService.deleteAllByStateId(stateId);
    }

    @Override
    public void sendStateReminder(StateReminder stateReminder) {
        String[] emails = stateReminder.getRecipients().split(",");
        for (String email : emails) {
            Map<String, Object> stateReminderMail = new HashMap<>();
            stateReminderMail.put(EmailPlaceHolders.TITLE, "You have a new State Reminder");
            stateReminderMail.put(EmailPlaceHolders.MESSAGE, stateReminder.getMessage());
            stateReminderMail.put(EmailPlaceHolders.PARA_ONE, String.format("Reminder sent by %s", stateReminder.getSender()));
            try {
                localEmailService.sendTemplateEmail(email, "New State Reminder", stateReminderMail);
            } catch (MessagingException | UnsupportedEncodingException e) {
                localLogger.warn("state reminder with id: {} for stateId: {} could not be sent", stateReminder.getId(), stateReminder.getStateId());
                e.printStackTrace();
            }

        }


    }
}
