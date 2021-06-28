package com.prodnees.core.action;

import com.prodnees.core.domain.doc.NeesDoc;
import com.prodnees.core.dto.DocumentDto;
import com.prodnees.core.model.DocumentModel;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.List;

public interface DocumentAction {

    DocumentModel save(NeesDoc neesDoc);

    DocumentModel update(DocumentDto dto);

    DocumentModel addNew(@Nullable String description, MultipartFile file) throws IOException;

    NeesDoc getById(int id);

    DocumentModel getModelById(int id);

    DocumentModel getByName(String name);

    List<DocumentModel> getAllByUserId(int userId);

    boolean existsByName(String name);

    void deleteById(int id);
}
