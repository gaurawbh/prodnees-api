package com.prodnees.service.rels;

import com.prodnees.domain.rels.StateRawProduct;
import java.util.List;
import java.util.Optional;

public interface StateRawProductService {

    StateRawProduct save(StateRawProduct stateRawProduct);

    Optional<StateRawProduct> getByStateIdAndRawProductId(int stateId, int rawProductId);

    List<StateRawProduct> getAllByStateId(int stateId);

    List<StateRawProduct> getAllByRawProductId(int rawProductId);

}
