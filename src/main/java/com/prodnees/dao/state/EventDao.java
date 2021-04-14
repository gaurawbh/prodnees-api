package com.prodnees.dao.state;

import com.prodnees.domain.state.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventDao extends JpaRepository<Event, Integer> {

    Event getById(int id);

    List<Event> getAllByBatchId(int batchId);

    List<Event> getAllByStateId(int stateId);

    Event getByBatchIdAndName(int batchId, String name);

    boolean existsByBatchId(int batchId);
}
