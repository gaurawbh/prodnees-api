package com.prodnees.core.service.doc;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.core.domain.doc.NeesDocType;

import java.util.List;

public interface NeesDocTypeService {

    NeesDocType save(NeesDocType docType);

    NeesDocType getById(int id);

    NeesDocType getByName(String name);

    void deleteById(int id);

    List<NeesDocType> findAll();

    List<String> extractSubTypes(NeesDocType neesDocType) throws JsonProcessingException;
}
