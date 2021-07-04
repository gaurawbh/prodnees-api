package com.prodnees.core.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.core.domain.doc.NeesDoctype;

import java.util.List;
import java.util.Map;

public interface NeesDocTypeAction {

    NeesDoctype save(NeesDoctype docType);

    Map<String, Object> getById(int id) throws JsonProcessingException;

    NeesDoctype getByName(String name);

    void deleteById(int id);

    List<Map<String, Object>> findAll();

    List<String> extractSubTypes(NeesDoctype neesDocType) throws JsonProcessingException;

    List<String> extractSubTypes(int docTypeId) throws JsonProcessingException;
}
