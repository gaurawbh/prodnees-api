package com.prodnees.action.impl;

import com.prodnees.action.DocumentAction;
import com.prodnees.controller.DocumentController;
import com.prodnees.domain.Document;
import com.prodnees.domain.rels.DocumentRight;
import com.prodnees.model.DocumentModel;
import com.prodnees.service.DocumentService;
import com.prodnees.service.rels.DocumentRightService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
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
     * Does not build with userId and ObjectRightType
     *
     * @param document
     * @return
     */
    public static DocumentModel entityToModel(Document document) {
        return new DocumentModel()
                .setId(document.getId())
                .setName(document.getName())
                .setDocumentUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/load")
                        .queryParam("id", document.getId())
                        .toUriString())
                .setDocumentDownloadUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/download")
                        .queryParam("id", document.getId())
                        .toUriString());
    }

    /**
     * Builds  a complete model including the userId
     *
     * @param documentRight
     * @return
     */
    public DocumentModel entityToModel(DocumentRight documentRight) {
        Document document = documentService.getById(documentRight.getDocumentId());
        return new DocumentModel()
                .setId(document.getId())
                .setName(document.getName())
                .setDocumentUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/load")
                        .queryParam("id", document.getId())
                        .toUriString())
                .setDocumentDownloadUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/download")
                        .queryParam("id", document.getId())
                        .toUriString())
                .setObjectRightType(documentRight.getDocumentRightsType());
    }
}
