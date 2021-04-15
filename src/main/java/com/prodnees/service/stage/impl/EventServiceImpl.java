package com.prodnees.service.stage.impl;

import com.prodnees.dao.stage.EventDao;
import com.prodnees.domain.stage.Event;
import com.prodnees.service.stage.EventService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;

    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public boolean existsByBatchId(int batchId) {
        return eventDao.existsByBatchId(batchId);
    }

    @Override
    public Event save(Event event) {
        return eventDao.save(event);
    }

    @Override
    public Event getById(int id) {
        return eventDao.getById(id);
    }

    @Override
    public List<Event> getAllByBatchId(int batchId) {
        return eventDao.getAllByBatchId(batchId);
    }

    @Override
    public List<Event> getAllByStageId(int stageId) {
        return eventDao.getAllByStageId(stageId);
    }

    @Override
    public Event getByBatchIdAndName(int batchId, String name) {
        return eventDao.getByBatchIdAndName(batchId, name);
    }
}
