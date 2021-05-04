package com.prodnees.action.stage;

import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.stage.StageReminder;

import java.util.List;
import java.util.Optional;

public interface StageReminderAction {

    Optional<StageReminder> findById(int id);

    StageReminder save(StageReminder stageReminder);

    List<StageReminder> getAllByStageId(int stageId);

    List<StageReminder> getAllBySender(String senderEmail);

    List<StageReminder> getAllByStageIdAndStageState(int stageId, StageState stageState);

    void deleteById(int id);

    void deleteAllByStageId(int stageId);

    void sendStageReminder(StageReminder stageReminder);
}
