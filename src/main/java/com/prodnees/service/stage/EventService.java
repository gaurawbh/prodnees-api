package com.prodnees.service.stage;

import com.prodnees.domain.stage.Event;
import java.util.List;

public interface EventService {

    boolean existsByBatchId(int batchId);

    Event save(Event event);

    Event getById(int id);

    List<Event> getAllByBatchId(int batchId);

    List<Event> getAllByStageId(int stageId);

    Event getByBatchIdAndName(int batchId, String name);

}
