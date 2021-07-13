package com.prodnees.shelf.service;

import com.prodnees.shelf.domain.RawMaterial;

import java.util.List;

public interface RawProductService {

    RawMaterial save(RawMaterial rawMaterial);

    RawMaterial getById(int id);

    RawMaterial getByName(String name);

    List<RawMaterial> getAllByStageId(int id);
}
