package com.prodnees.action.stage;

import com.prodnees.domain.stage.Event;
import java.util.List;

public interface EventAction {

    boolean existsByBatchId(int batchId);

    Event save(Event event);

    Event getById(int id);

    List<Event> getAllByBatchId(int batchId);

    List<Event> getAllByStageId(int stageId);

}
