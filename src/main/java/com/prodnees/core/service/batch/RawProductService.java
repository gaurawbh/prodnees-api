package com.prodnees.core.service.batch;

import com.prodnees.shelf.domain.RawProduct;

import java.util.List;

public interface RawProductService {

    RawProduct save(RawProduct rawProduct);

    RawProduct getById(int id);

    RawProduct getByName(String name);

    List<RawProduct> getAllByStageId(int id);
}
