package com.prodnees.qc.action.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.service.user.UserAttributesService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.ValidatorUtil;
import com.prodnees.core.web.exception.NeesBadRequestException;
import com.prodnees.qc.action.InspectionTypeAction;
import com.prodnees.qc.domain.InspectionType;
import com.prodnees.qc.dto.InspectionTypeDto;
import com.prodnees.qc.service.InspectionTypeService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InspectionTypeActionImpl implements InspectionTypeAction {
    private final InspectionTypeService inspectionTypeService;
    private final UserAttributesService userAttributesService;

    public InspectionTypeActionImpl(InspectionTypeService inspectionTypeService,
                                    UserAttributesService userAttributesService) {
        this.inspectionTypeService = inspectionTypeService;
        this.userAttributesService = userAttributesService;
    }

    @Override
    public InspectionType addNew(InspectionTypeDto dto) {
        UserAttributes userAttributes = userAttributesService.getByUserId(RequestContext.getUserId());
        InspectionType inspectionType = new InspectionType().setName(dto.getName())
                .setSummary(dto.getSummary())
                .setCreatedBy(userAttributes.getUserId())
                .setCreatedByName(userAttributes.getFirstName() + " " + userAttributes.getLastName());
        return inspectionTypeService.save(inspectionType);
    }

    @Override
    public InspectionType update(InspectionTypeDto dto) {
        LocalAssert.isTrue(dto.getId() != null, "invalid or null id");
        InspectionType inspectionType = inspectionTypeService.getById(dto.getId());
        if (!dto.getName().equals(inspectionType.getName())) {
            LocalAssert.isFalse(inspectionTypeService.existsByName(dto.getName()), String.format("InspectionType with name: [%s] already exists", dto.getName()));
        }
        inspectionType.setName(dto.getName())
                .setSummary(ValidatorUtil.ifValidStringOrElse(dto.getSummary(), inspectionType.getSummary()));
        return inspectionTypeService.save(inspectionType);
    }

    @Override
    public InspectionType getById(int id) {
        return inspectionTypeService.getById(id);
    }

    @Override
    public void deleteById(int id) {
        try {
            inspectionTypeService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new NeesBadRequestException(String.format("InspectionType with id: %d does not exist", id));
        }
    }

    @Override
    public List<InspectionType> findAll() {
        return inspectionTypeService.findAll();
    }
}
