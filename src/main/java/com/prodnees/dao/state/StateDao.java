package com.prodnees.dao.state;

import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.State;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StateDao extends JpaRepository<State, Integer> {

    State getById(int id);

    State getByName(String name);

    List<State> getAllByBatchProductId(int batchProductId);

    List<State> getAllByBatchProductIdAndStatus(int batchProductId, StateStatus status);

    boolean existsByBatchProductId(int batchProductId);
}
