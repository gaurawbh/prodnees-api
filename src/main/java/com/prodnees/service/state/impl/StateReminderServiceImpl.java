package com.prodnees.service.state.impl;

import com.prodnees.dao.state.StateReminderDao;
import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.StateReminder;
import com.prodnees.service.state.StateReminderService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StateReminderServiceImpl implements StateReminderService {
    private final StateReminderDao stateReminderDao;

    public StateReminderServiceImpl(StateReminderDao stateReminderDao) {
        this.stateReminderDao = stateReminderDao;
    }

    @Override
    public Optional<StateReminder> findById(int id) {
         return stateReminderDao.findById(id);
    }

    @Override
    public StateReminder save(StateReminder stateReminder) {
        return stateReminderDao.save(stateReminder);
    }

    @Override
    public List<StateReminder> getAllByStateId(int stateId) {
        return stateReminderDao.getAllByStateId(stateId);
    }

    @Override
    public List<StateReminder> getAllByStateIdAndStateStatus(int stateId, StateStatus stateStatus) {
        return stateReminderDao.getAllByStateIdAndStateStatus(stateId, stateStatus);
    }

    @Override
    public void deleteById(int id) {
        stateReminderDao.deleteById(id);
    }

    @Override
    public void deleteAllByStateId(int stateId) {
        stateReminderDao.deleteAllByStateId(stateId);
    }
}
