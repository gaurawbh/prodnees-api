package com.prodnees.qc.action;

import com.prodnees.qc.domain.InspectionType;

import java.util.List;

public interface InspectionTypeAction {

    InspectionType addNew(InspectionType inspectionType);


    InspectionType update(InspectionType inspectionType);

    InspectionType getById( int id);

    void deleteById(int id);

    List<InspectionType> findAll();
}
