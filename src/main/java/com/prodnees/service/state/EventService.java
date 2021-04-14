package com.prodnees.service.state;

import com.prodnees.domain.state.Event;
import java.util.List;

public interface EventService {

    boolean existsByBatchId(int batchId);

    Event save(Event event);

    Event getById(int id);

    List<Event> getAllByBatchId(int batchId);

    List<Event> getAllByStateId(int stateId);

    Event getByBatchIdAndName(int batchId, String name);


}
