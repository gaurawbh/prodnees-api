package com.prodnees.dao.state;

import com.prodnees.domain.enums.StateStatus;
import com.prodnees.domain.state.State;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StateDao extends JpaRepository<State, Integer> {

    State getById(int id);

    State getByName(String name);

    State getByBatchIdAndIndex(int batchId, int index);

    List<State> getAllByBatchId(int batchId);

    List<State> getAllByBatchIdAndStatus(int batchId, StateStatus status);

    List<State> getAllByBatchIdAndIndexGreaterThan(int batchId, int index);

    List<State> getAllByBatchIdAndIndexStartingWith(int batchId, int index);

    List<State> getAllByBatchIdAndIndexLessThan(int batchId, int index);

    boolean existsByBatchId(int batchId);

    int countByBatchId(int batchId);

    boolean existsByBatchIdAndIndex(int batchId, int i);

    void deleteAllByBatchId(int batchId);

    void deleteByBatchIdAndIndex(int batchId, int index);
}
