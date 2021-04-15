package com.prodnees.dao.state;

import com.prodnees.domain.enums.StageState;
import com.prodnees.domain.state.StageReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StageReminderDao extends JpaRepository<StageReminder, Integer> {

    List<StageReminder> getAllByStageId(int stageId);

    List<StageReminder> getAllByStageIdAndStageState(int stageId, StageState stageState);

    void deleteAllByStageId(int stageId);

    List<StageReminder> getAllBySender(String senderEmail);
}
