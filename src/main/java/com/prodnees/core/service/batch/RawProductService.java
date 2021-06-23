package com.prodnees.core.service.batch;

import com.prodnees.core.domain.batch.RawProduct;

import java.util.List;

public interface RawProductService {

    RawProduct save(RawProduct rawProduct);

    RawProduct getById(int id);

    RawProduct getByName(String name);

    List<RawProduct> getAllByStageId(int id);
}
