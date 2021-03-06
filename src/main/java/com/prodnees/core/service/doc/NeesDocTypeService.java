package com.prodnees.core.service.doc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.core.domain.doc.NeesDoctype;

import java.util.List;

public interface NeesDocTypeService {

    NeesDoctype save(NeesDoctype docType);

    NeesDoctype getById(int id);

    NeesDoctype getByName(String name);

    void deleteById(int id);

    List<NeesDoctype> findAll();

    List<String> extractSubTypes(NeesDoctype neesDocType) throws JsonProcessingException;
}
