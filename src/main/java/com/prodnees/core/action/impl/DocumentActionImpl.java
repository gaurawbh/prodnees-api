package com.prodnees.core.action.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.DocumentAction;
import com.prodnees.core.config.constants.APIErrors;
import com.prodnees.core.controller.DocumentController;
import com.prodnees.core.domain.NeesContentType;
import com.prodnees.core.domain.doc.DocumentPermission;
import com.prodnees.core.domain.doc.NeesDoc;
import com.prodnees.core.domain.doc.UserDocumentRight;
import com.prodnees.core.dto.DocumentDto;
import com.prodnees.core.model.DocumentModel;
import com.prodnees.core.service.NeesDocumentService;
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

    private final NeesDocumentService neesDocumentService;
    private final DocumentRightService documentRightService;

    public DocumentActionImpl(NeesDocumentService neesDocumentService,
                              DocumentRightService documentRightService) {
        this.neesDocumentService = neesDocumentService;
        this.documentRightService = documentRightService;
    }


    @Override
    public DocumentModel save(NeesDoc neesDoc) {
        return entityToModel(neesDocumentService.save(neesDoc));
    }

    @Override
    public DocumentModel update(DocumentDto dto) {
        int userId = RequestContext.getUserId();
        UserDocumentRight userDocumentRight = documentRightService.findByDocumentIdAndUserId(dto.getId(), userId).orElseThrow(NeesNotFoundException::new);
        LocalAssert.isTrue(documentRightService.hasEditRights(userDocumentRight), APIErrors.UPDATE_DENIED);
        NeesDoc neesDoc = neesDocumentService.getById(dto.getId()).setDescription(dto.getDescription());
        return save(neesDoc);
    }

    @Override
    public DocumentModel addNew(@Nullable String description, MultipartFile file) throws IOException {
        int userId = RequestContext.getUserId();
        int nextId = neesDocumentService.getNextId();
        String name = "Document-" + nextId;
        if (!NeesContentType.supportedContentTypes().contains(file.getContentType())) {
            throw new NeesBadRequestException(String.format("Unsupported file type. Supported file types: %s", NeesContentType.supportedContentTypes()));
        }
        NeesDoc neesDoc = new NeesDoc()
                .setName(name)
                .setDescription(description)
                .setMimeContentType(file.getContentType())
                .setCreatedDatetime(LocalDateTime.now(ZoneId.of("UTC")))
                .setFile(file.getBytes());
        neesDoc = neesDocumentService.save(neesDoc);
        UserDocumentRight userDocumentRight = new UserDocumentRight()
                .setUserId(userId)
                .setDocumentId(neesDoc.getId())
                .setDocumentPermission(DocumentPermission.Delete);
        documentRightService.save(userDocumentRight);
        return entityToModel(neesDoc, userDocumentRight);
    }

    @Override
    public NeesDoc getById(int id) {
        return neesDocumentService.getById(id);
    }

    @Override
    public DocumentModel getModelById(int id) {
        return entityToModel(neesDocumentService.getById(id));
    }

    @Override
    public DocumentModel getByName(String name) {
        return entityToModel(neesDocumentService.getByName(name));
    }

    @Override
    public List<DocumentModel> getAllByUserId(int userId) {
        List<UserDocumentRight> userDocumentRightList = documentRightService.getAllByUserId(userId);
        List<DocumentModel> documentModelList = new ArrayList<>();
        userDocumentRightList.forEach(documentRight -> documentModelList.add(entityToModel(documentRight)));
        return documentModelList;
    }

    @Override
    public boolean existsByName(String name) {
        return neesDocumentService.existsByName(name);
    }

    @Override
    public void deleteById(int id) {
        neesDocumentService.deleteById(id);
    }


    /**
     * Builds  a complete model including the userId
     *
     * @param userDocumentRight
     * @return
     */
    public DocumentModel entityToModel(NeesDoc neesDoc, UserDocumentRight userDocumentRight) {
        DocumentModel model = entityToModel(neesDoc);
        model.setDocumentPermission(userDocumentRight.getDocumentPermission());
        return model;
    }


    /**
     * Builds  a complete model including the userId
     *
     * @param userDocumentRight
     * @return
     */
    public DocumentModel entityToModel(UserDocumentRight userDocumentRight) {
        NeesDoc neesDoc = neesDocumentService.getById(userDocumentRight.getDocumentId());
        DocumentModel model = entityToModel(neesDoc);
        model.setDocumentPermission(userDocumentRight.getDocumentPermission());
        return model;
    }

    /**
     * Does not build with userId and ObjectRightType
     *
     * @param neesDoc
     * @return
     */
    public DocumentModel entityToModel(NeesDoc neesDoc) {
        return new DocumentModel()
                .setId(neesDoc.getId())
                .setName(neesDoc.getName())
                .setDescription(neesDoc.getDescription())
                .setContentType(neesDoc.getMimeContentType())
                .setDocumentUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/load")
                        .queryParam("id", neesDoc.getId())
                        .toUriString())
                .setDocumentDownloadUrl(MvcUriComponentsBuilder.fromController(DocumentController.class)
                        .path("document/download")
                        .queryParam("id", neesDoc.getId())
                        .toUriString());
    }


}
