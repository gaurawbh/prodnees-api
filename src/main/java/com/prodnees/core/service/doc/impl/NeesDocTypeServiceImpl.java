package com.prodnees.core.service.doc.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prodnees.core.dao.doc.NeesDocTypeDao;
import com.prodnees.core.domain.doc.NeesDoctype;
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
    public NeesDoctype save(NeesDoctype docType) {
        return neesDocTypeDao.save(docType);
    }

    @Override
    public NeesDoctype getById(int id) {
        return neesDocTypeDao.findById(id)
                .orElseThrow(() -> new NeesNotFoundException(String.format("NeesDocType with id: %d not found", id)));
    }

    @Override
    public NeesDoctype getByName(String name) {
        return neesDocTypeDao.findByName(name)
                .orElseThrow(()-> new NeesNotFoundException(String.format("NeesDocType with name: %s not found", name)));
    }

    @Override
    public void deleteById(int id) {
        neesDocTypeDao.deleteById(id);

    }

    @Override
    public List<NeesDoctype> findAll() {
        return neesDocTypeDao.findAll();
    }

    @Override
    public List<String> extractSubTypes(NeesDoctype neesDocType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(neesDocType.getSubTypesJson(), List.class);
    }
}
