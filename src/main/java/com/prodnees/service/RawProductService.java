package com.prodnees.service;

import com.prodnees.domain.RawProduct;
import org.springframework.stereotype.Service;

public interface RawProductService {

    RawProduct save(RawProduct rawProduct);

    RawProduct getById(int id);

    RawProduct getByName(String name);

}
