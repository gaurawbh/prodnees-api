package com.prodnees.core.action.stage;

import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.StageReminder;
import com.prodnees.core.dto.stage.StageReminderDto;

import java.util.List;
import java.util.Optional;

public interface StageReminderAction {

    Optional<StageReminder> findById(int id);

    StageReminder save(StageReminder stageReminder);

    StageReminder addNew(StageReminderDto dto);

    StageReminder getById(int id);

    List<StageReminder> getAllByStageId(int stageId);

    List<StageReminder> getAllBySender(String senderEmail);

    List<StageReminder> getAllByStageIdAndStageState(int stageId, StageState stageState);

    void deleteById(int id);

    void deleteAllByStageId(int stageId);

    void sendStageReminder(StageReminder stageReminder);
}
