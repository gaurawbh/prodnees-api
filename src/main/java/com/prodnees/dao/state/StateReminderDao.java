package com.prodnees.dao.state;

import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.StateReminder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StateReminderDao extends JpaRepository<StateReminder, Integer> {

    List<StateReminder> getAllByStateId(int stateId);

    List<StateReminder> getAllByStateIdAndStateStatus(int stateId, StateStatus stateStatus);

    void deleteAllByStateId(int stateId);
}
