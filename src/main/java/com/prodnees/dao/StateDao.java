package com.prodnees.dao;

import com.prodnees.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateDao extends JpaRepository<State, Integer> {

    State getById(int id);

    State getByName(String name);

    List<State> getAllByBatchProductId(int batchProductId);

    List<State> getAllByBatchProductIdAndComplete(int batchProductId, boolean isComplete);
}
