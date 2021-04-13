package com.prodnees.service.state;

import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.StateReminder;
import java.util.List;
import java.util.Optional;

public interface StateReminderService {

    Optional<StateReminder> findById(int id);

    StateReminder save(StateReminder stateReminder);

    List<StateReminder> getAllByStateId(int stateId);

    List<StateReminder> getAllByStateIdAndStateStatus(int stateId, StateStatus stateStatus);

    void deleteById(int id);

    void deleteAllByStateId(int stateId);

    List<StateReminder> getAllBySender(String senderEmail);
}
