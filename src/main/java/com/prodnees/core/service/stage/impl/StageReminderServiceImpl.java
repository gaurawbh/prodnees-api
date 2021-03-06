package com.prodnees.core.service.stage.impl;

import com.prodnees.core.dao.stage.StageReminderDao;
import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.StageReminder;
import com.prodnees.core.service.stage.StageReminderService;
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
    public List<StageReminder> getAllByStageId(int stageId) {
        return stageReminderDao.getAllByStageId(stageId);
    }

    @Override
    public List<StageReminder> getAllByStageIdAndStateState(int stageId, StageState stageState) {
        return stageReminderDao.getAllByStageIdAndOnStageState(stageId, stageState);
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
