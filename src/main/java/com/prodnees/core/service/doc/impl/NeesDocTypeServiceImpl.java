package com.prodnees.core.service.doc.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodnees.core.dao.doc.NeesDocTypeDao;
import com.prodnees.core.domain.doc.NeesDocType;
import com.prodnees.core.service.doc.NeesDocTypeService;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NeesDocTypeServiceImpl implements NeesDocTypeService {
    private final NeesDocTypeDao neesDocTypeDao;

    public NeesDocTypeServiceImpl(NeesDocTypeDao neesDocTypeDao) {
        this.neesDocTypeDao = neesDocTypeDao;
    }

    @Override
    public NeesDocType save(NeesDocType docType) {
        return neesDocTypeDao.save(docType);
    }

    @Override
    public NeesDocType getById(int id) {
        return neesDocTypeDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("NeesDocType with id: %d not found", id)));
    }

    @Override
    public NeesDocType getByName(String name) {
        return neesDocTypeDao.findByName(name)
                .orElseThrow(()-> new NeesNotFoundException(String.format("NeesDocType with name: %s not found", name)));
    }

    @Override
    public void deleteById(int id) {

    }

    @Override
    public List<NeesDocType> findAll() {
        return null;
    }

    @Override
    public List<String> extractSubTypes(NeesDocType neesDocType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(neesDocType.getSubTypesJson(), List.class);
    }
}
