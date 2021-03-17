package com.prodnees.dao;

import com.prodnees.domain.RawProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawProductDao extends JpaRepository<RawProduct, Integer> {

    RawProduct getById(int id);

    RawProduct getByName(String name);

}
