package com.prodnees.core.action.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.DocumentAction;
import com.prodnees.core.config.constants.APIErrors;
import com.prodnees.core.controller.DocumentController;
import com.prodnees.core.domain.Document;
import com.prodnees.core.domain.NeesContentType;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.rels.DocumentRight;
import com.prodnees.core.dto.DocumentDto;
import com.prodnees.core.model.DocumentModel;
import com.prodnees.core.service.DocumentService;
import com.prodnees.core.service.rels.DocumentRightService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.web.exception.NeesBadRequestException;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentActionImpl implements DocumentAction {

    private final DocumentService documentService;
    private final DocumentRightService documentRightService;

    public DocumentActionImpl(DocumentService documentService,
                              DocumentRightService documentRightService) {
        this.documentService = documentService;
        this.documentRightService = documentRightService;
    }


    @Override
    public DocumentModel save(Document document) {
        return entityToModel(documentService.save(document));
    }

    @Override
    public DocumentModel update(DocumentDto dto) {
        int userId = RequestContext.getUserId();
        DocumentRight documentRight = documentRightService.findByDocumentIdAndUserId(dto.getId(), userId).orElseThrow(NeesNotFoundException::new);
        LocalAssert.isTrue(documentRightService.hasEditRights(documentRight), APIErrors.UPDATE_DENIED);
        Document document = documentService.getById(dto.getId()).setDescription(dto.getDescription());
        return save(document);
    }

    @Override
    public DocumentModel addNew(@Nullable String description, MultipartFile file) throws IOException {
        int userId = RequestContext.getUserId();
        int nextId = documentService.getNextId();
        String name = "Document-" + nextId;
        if (!NeesContentType.supportedContentTypes().contains(file.getContentType())) {
            throw new NeesBadRequestException(String.format("Unsupported file type. Supported file types: %s", NeesContentType.supportedContentTypes()));
        }
        Document document = new Document()
                .setName(name)
                .setDescription(description)
                .setContentType(file.getContentType())
                .setCreatedDatetime(LocalDateTime.now(ZoneId.of("UTC")))
                .setFile(file.getBytes());
        document = documentService.save(document);
        DocumentRight documentRight = new DocumentRight()
                .setUserId(userId)
                .setDocumentId(document.getId())
                .setDocumentRightsType(ObjectRight.OWNER);
        documentRightService.save(documentRight);
        return entityToModel(document, documentRight);
    }

    @Override
    public Document getById(int id) {
        return documentService.getById(id);
    }

    @Override
    public DocumentModel getModelById(int id) {
        return entityToModel(documentService.getById(id));
    }

    @Override
    public DocumentModel getByName(String name) {
        return entityToModel(documentService.getByName(name));
    }

    @Override
    public List<DocumentModel> getAllByUserId(int userId) {
        List<DocumentRight> documentRightList = documentRightService.getAllByUserId(userId);
        List<DocumentModel> documentModelList = new ArrayList<>();
        documentRightList.forEach(documentRight -> documentModelList.add(entityToModel(documentRight)));
        return documentModelList;
    }

    @Override
    public boolean existsByName(String name) {
        return documentService.existsByName(name);
    }

    @Override
    public void deleteById(int id) {
        documentService.deleteById(id);
    }


    /**
     * Builds  a complete model including the userId
     *
     * @param documentRight
     * @return
     */
    public DocumentModel entityToModel(Document document, DocumentRight documentRight) {
        DocumentModel model = entityToModel(document);
        model.setObjectRight(documentRight.getDocumentRightsType());
        return model;
    }


    /**
     * Builds  a complete model including the userId
     *
     * @param documentRight
     * @return
     */
    public DocumentModel entityToModel(DocumentRight documentRight) {
        Document document = documentService.getById(documentRight.getDocumentId());
        DocumentModel model = entityToModel(document);
        model.setObjectRight(documentRight.getDocumentRightsType());
        return model;
    }

    /**
     * Does not build with userId and ObjectRightType
     *
     * @param document
     * @return
     */
    public DocumentModel entityToModel(Document document) {
        return new DocumentModel()
                .setId(document.getId())
                .setName(document.getName())
                .setDescription(document.getDescription())
                .setContentType(document.getContentType())
                .setDocumentUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/load")
                        .queryParam("id", document.getId())
                        .toUriString())
                .setDocumentDownloadUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/download")
                        .queryParam("id", document.getId())
                        .toUriString());
    }


}
