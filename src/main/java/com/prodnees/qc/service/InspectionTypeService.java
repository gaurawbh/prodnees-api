package com.prodnees.qc.service;

import com.prodnees.qc.domain.InspectionType;

import java.util.List;

public interface InspectionTypeService {

    InspectionType save(InspectionType inspectionType);

    InspectionType getById( int id);

    void deleteById(int id);

    List<InspectionType> findAll();
}
