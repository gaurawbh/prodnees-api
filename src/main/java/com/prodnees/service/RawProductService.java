package com.prodnees.service;

import com.prodnees.domain.RawProduct;
import java.util.List;

public interface RawProductService {

    RawProduct save(RawProduct rawProduct);

    RawProduct getById(int id);

    RawProduct getByName(String name);

    List<RawProduct> getAllByStateId(int id);
}
