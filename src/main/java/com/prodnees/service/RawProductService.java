package com.prodnees.service;

import com.prodnees.domain.RawProduct;

public interface RawProductService {

    RawProduct save(RawProduct rawProduct);

    RawProduct getById(int id);

    RawProduct getByName(String name);

}
