package com.prodnees.core.action;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.core.domain.doc.NeesDoc;
import com.prodnees.core.dto.NeesDocDto;
import com.prodnees.core.model.DocumentModel;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DocumentAction {

    DocumentModel save(NeesDoc neesDoc);

    DocumentModel update(NeesDocDto dto);

    Map<String, Object> addNew(@Nullable String docType, @Nullable String docSubType, MultipartFile file) throws IOException;

    NeesDoc getById(int id);

    DocumentModel getModelById(int id);

    DocumentModel getByName(String name);

    List<DocumentModel> getAllByUserId(int userId);

    boolean existsByName(String name);

    void deleteById(int id);

    List<Map<String, Object>> getValidDocObjects(int id);

    Map<String, Object> reclassify(int id, String doctype,@Nullable String docSubtype) throws JsonProcessingException;
}
