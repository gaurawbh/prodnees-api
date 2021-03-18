package com.prodnees.dao.rels;

import com.prodnees.domain.rels.StateRawProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StateRawProductDao extends JpaRepository<StateRawProduct, Integer> {
    Optional<StateRawProduct> findByStateIdAndRawProductId(int stateId, int rawProductId);

    List<StateRawProduct> getAllByStateId(int stateId);

    List<StateRawProduct> getAllByRawProductId(int rawProductId);

}
