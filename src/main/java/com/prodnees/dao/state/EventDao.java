package com.prodnees.dao.state;

import com.prodnees.domain.state.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventDao extends JpaRepository<Event, Integer> {

    Event getById(int id);

    List<Event> getAllByBatchProductId(int batchProductId);

    List<Event> getAllByStateId(int stateId);

    Event getByBatchProductIdAndName(int batchProductId, String name);

    boolean existsByBatchProductId(int batchProductId);
}
