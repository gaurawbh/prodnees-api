package com.prodnees.service.rels;

import com.prodnees.domain.rels.StateRawProduct;
import java.util.List;

public interface StateRawProductService {

    StateRawProduct save(StateRawProduct stateRawProduct);

    StateRawProduct getByStateIdAndRawProductId(int stateId, int rawProductId);

    List<StateRawProduct> getAllByStateId(int stateId);

    List<StateRawProduct> getAllByRawProductId(int stateId);


}
