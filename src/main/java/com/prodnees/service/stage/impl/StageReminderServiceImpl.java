package com.prodnees.service.stage.impl;

import com.prodnees.dao.state.StageReminderDao;
import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.state.StageReminder;
import com.prodnees.service.stage.StageReminderService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StageReminderServiceImpl implements StageReminderService {
    private final StageReminderDao stageReminderDao;

    public StageReminderServiceImpl(StageReminderDao stageReminderDao) {
        this.stageReminderDao = stageReminderDao;
    }

    @Override
    public Optional<StageReminder> findById(int id) {
        return stageReminderDao.findById(id);
    }

    @Override
    public StageReminder save(StageReminder stageReminder) {
        return stageReminderDao.save(stageReminder);
    }

    @Override
    public List<StageReminder> getAllByStageId(int stateId) {
        return stageReminderDao.getAllByStageId(stateId);
    }

    @Override
    public List<StageReminder> getAllByStageIdAndStateState(int stateId, StageState stageState) {
        return stageReminderDao.getAllByStageIdAndStageState(stateId, stageState);
    }

    @Override
    public void deleteById(int id) {
        stageReminderDao.deleteById(id);
    }

    @Override
    public void deleteAllByStageId(int stageId) {
        stageReminderDao.deleteAllByStageId(stageId);
    }

    @Override
    public List<StageReminder> getAllBySender(String senderEmail) {
        return stageReminderDao.getAllBySender(senderEmail);
    }
}
