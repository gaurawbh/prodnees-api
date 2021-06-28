package com.prodnees.core.dao.stage;

import com.prodnees.core.domain.enums.StageState;
import com.prodnees.core.domain.stage.StageReminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StageReminderDao extends JpaRepository<StageReminder, Integer> {

    List<StageReminder> getAllByStageId(int stageId);

    List<StageReminder> getAllByStageIdAndOnStageState(int stageId, StageState stageState);

    void deleteAllByStageId(int stageId);

    List<StageReminder> getAllBySender(String senderEmail);
}
