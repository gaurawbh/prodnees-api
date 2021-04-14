package com.prodnees.service.batch;

import com.prodnees.domain.batchproduct.RawProduct;
import java.util.List;

public interface RawProductService {

    RawProduct save(RawProduct rawProduct);

    RawProduct getById(int id);

    RawProduct getByName(String name);

    List<RawProduct> getAllByStateId(int id);
}
