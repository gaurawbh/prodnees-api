package com.prodnees.core.action.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodnees.core.action.NeesDocTypeAction;
import com.prodnees.core.domain.doc.NeesDoctype;
import com.prodnees.core.model.NeesDocProps;
import com.prodnees.core.service.doc.NeesDocTypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NeesDocTypeActionImpl implements NeesDocTypeAction {
    private final NeesDocTypeService neesDocTypeService;

    public NeesDocTypeActionImpl(NeesDocTypeService neesDocTypeService) {
        this.neesDocTypeService = neesDocTypeService;
    }

    @Override
    public NeesDoctype save(NeesDoctype docType) {
        return neesDocTypeService.save(docType);
    }

    @Override
    public Map<String, Object> getById(int id) throws JsonProcessingException {
        return entityToModel(neesDocTypeService.getById(id));
    }

    @Override
    public NeesDoctype getByName(String name) {
        return neesDocTypeService.getByName(name);
    }

    @Override
    public void deleteById(int id) {
        neesDocTypeService.deleteById(id);
    }

    @Override
    public List<Map<String, Object>> findAll() {
        return entityToModel(neesDocTypeService.findAll());
    }

    @Override
    public List<String> extractSubTypes(int docTypeId) throws JsonProcessingException {
        NeesDoctype docType = neesDocTypeService.getById(docTypeId);
        return neesDocTypeService.extractSubTypes(docType);
    }

    @Override
    public List<String> extractSubTypes(NeesDoctype neesDocType) throws JsonProcessingException {
        return neesDocTypeService.extractSubTypes(neesDocType);
    }

    Map<String, Object> entityToModel(NeesDoctype doctype) throws JsonProcessingException {

        Map<String, Object> doctypeM = new HashMap<>();
        doctypeM.put(NeesDocProps.DoctypeProps.id.name(), doctype.getId());
        doctypeM.put(NeesDocProps.DoctypeProps.name.name(), doctype.getName());
        doctypeM.put(NeesDocProps.DoctypeProps.description.name(), doctype.getDescription());
        doctypeM.put(NeesDocProps.DoctypeProps.subTypes.name(), new ObjectMapper().readValue(doctype.getSubTypesJson(), List.class));
        doctypeM.put(NeesDocProps.DoctypeProps.active.name(), doctype.isActive());
        doctypeM.put(NeesDocProps.DoctypeProps.sys.name(), doctype.isSys());
        return doctypeM;

    }

    List<Map<String, Object>> entityToModel(List<NeesDoctype> doctypes) {
        List<Map<String, Object>> doctypesM = new ArrayList<>();
        doctypes.forEach(doctype -> {
            try {
                doctypesM.add(entityToModel(doctype));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
        return doctypesM;
    }

}
