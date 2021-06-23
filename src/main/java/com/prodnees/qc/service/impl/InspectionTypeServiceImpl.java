package com.prodnees.qc.service.impl;

import com.prodnees.core.web.exception.NeesNotFoundException;
import com.prodnees.qc.dao.InspectionTypeDao;
import com.prodnees.qc.domain.InspectionType;
import com.prodnees.qc.service.InspectionTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionTypeServiceImpl implements InspectionTypeService {
    private final InspectionTypeDao inspectionTypeDao;

    public InspectionTypeServiceImpl(InspectionTypeDao inspectionTypeDao) {
        this.inspectionTypeDao = inspectionTypeDao;
    }

    @Override
    public InspectionType save(InspectionType inspectionType) {
        return inspectionTypeDao.save(inspectionType);
    }

    @Override
    public InspectionType getById(int id) {
        return inspectionTypeDao.findById(id)
                .orElseThrow(()->new NeesNotFoundException(String.format("Inspection Type with id: %d not found", id)));
    }

    @Override
    public void deleteById(int id) {
        inspectionTypeDao.deleteById(id);

    }

    @Override
    public List<InspectionType> findAll() {
        return inspectionTypeDao.findAll();
    }

    @Override
    public boolean existsByName(String name) {
        return inspectionTypeDao.existsByName(name);
    }
}
