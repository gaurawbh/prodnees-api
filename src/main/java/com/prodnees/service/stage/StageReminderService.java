package com.prodnees.service.stage;

import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.state.StageReminder;
import java.util.List;
import java.util.Optional;

public interface StageReminderService {

    Optional<StageReminder> findById(int id);

    StageReminder save(StageReminder stageReminder);

    List<StageReminder> getAllByStageId(int stateId);

    List<StageReminder> getAllByStageIdAndStateState(int stageId, StageState stageState);

    void deleteById(int id);

    void deleteAllByStageId(int stageId);

    List<StageReminder> getAllBySender(String senderEmail);
}
