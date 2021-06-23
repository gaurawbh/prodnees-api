package com.prodnees.qc.action;

import com.prodnees.qc.domain.InspectionType;
import com.prodnees.qc.dto.InspectionTypeDto;

import java.util.List;

public interface InspectionTypeAction {

    InspectionType addNew(InspectionTypeDto inspectionType);

    InspectionType update(InspectionTypeDto inspectionType);

    InspectionType getById( int id);

    void deleteById(int id);

    List<InspectionType> findAll();
}
