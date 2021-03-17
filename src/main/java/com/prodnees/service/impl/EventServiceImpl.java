package com.prodnees.service.impl;

import com.prodnees.dao.EventDao;
import com.prodnees.domain.Event;
import com.prodnees.service.EventService;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class EventServiceImpl implements EventService {
    private final EventDao eventDao;

    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    @Override
    public Event save(Event event) {
        return eventDao.save(event);
    }

    @Override
    public Event getById(int id) {
        return null;
    }

    @Override
    public List<Event> getAllByBatchProductId(int batchProductId) {
        return null;
    }

    @Override
    public List<Event> getAllByStateId(int batchProductId) {
        return null;
    }
}
