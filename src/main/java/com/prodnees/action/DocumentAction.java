package com.prodnees.action;

import com.prodnees.domain.Document;
import com.prodnees.dto.DocumentDto;
import com.prodnees.model.DocumentModel;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public interface DocumentAction {

    DocumentModel save(Document document);

    DocumentModel update(DocumentDto dto);

    DocumentModel addNew(@Nullable String description, MultipartFile file) throws IOException;

    Document getById(int id);

    DocumentModel getModelById(int id);

    DocumentModel getByName(String name);

    List<DocumentModel> getAllByUserId(int userId);

    boolean existsByName(String name);

    void deleteById(int id);
}
