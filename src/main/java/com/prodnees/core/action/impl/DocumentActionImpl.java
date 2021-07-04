package com.prodnees.core.action.impl;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.DocumentAction;
import com.prodnees.core.config.constants.APIErrors;
import com.prodnees.core.config.constants.DateTimeFormats;
import com.prodnees.core.controller.DocumentController;
import com.prodnees.core.domain.NeesContentType;
import com.prodnees.core.domain.doc.DocumentPermission;
import com.prodnees.core.domain.doc.NeesDoc;
import com.prodnees.core.domain.doc.NeesDoctype;
import com.prodnees.core.domain.doc.NeesFile;
import com.prodnees.core.domain.doc.UserDocumentRight;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.dto.DocumentDto;
import com.prodnees.core.model.DocumentModel;
import com.prodnees.core.model.NeesDocProps;
import com.prodnees.core.model.NeesObjProps;
import com.prodnees.core.service.NeesDocumentService;
import com.prodnees.core.service.doc.NeesDocTypeService;
import com.prodnees.core.service.doc.NeesFileService;
import com.prodnees.core.service.rels.DocumentRightService;
import com.prodnees.core.service.user.UserAttributesService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.LocalStringUtils;
import com.prodnees.core.web.exception.NeesBadRequestException;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.Nullable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentActionImpl implements DocumentAction {

    private final NeesDocumentService neesDocumentService;
    private final DocumentRightService documentRightService;
    private final NeesDocTypeService neesDocTypeService;
    private final NeesFileService neesFileService;
    private final UserAttributesService userAttributesService;

    public DocumentActionImpl(NeesDocumentService neesDocumentService,
                              DocumentRightService documentRightService,
                              NeesDocTypeService neesDocTypeService,
                              NeesFileService neesFileService,
                              UserAttributesService userAttributesService) {
        this.neesDocumentService = neesDocumentService;
        this.documentRightService = documentRightService;
        this.neesDocTypeService = neesDocTypeService;
        this.neesFileService = neesFileService;
        this.userAttributesService = userAttributesService;
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

    /**
     * Checks:
     * <p>if doctype is not empty, check the doctype exists</p>
     * <p>if docSubType is not empty, check if the docSubType is the valid for the docType</p>
     * <p>docSubType will be ignored if docType is null</p>
     *
     * @param docType
     * @param docSubType
     * @param file
     * @return
     * @throws IOException
     */

    @Override
    public Map<String, Object> addNew(@Nullable String docType, @Nullable String docSubType, MultipartFile file) throws IOException {
        NeesDoc neesDoc = new NeesDoc();
        if (LocalStringUtils.hasValue(docType)) {
            NeesDoctype neesDocType = neesDocTypeService.getByName(docType);
            neesDoc.setDocType(docType);
            List<String> docSubTypesList = neesDocTypeService.extractSubTypes(neesDocType);
            if (LocalStringUtils.hasValue(docSubType)) {
                if (!docSubTypesList.contains(docSubType)) {
                    throw new NeesBadRequestException(String.format("Invalid docSubType: %s for docType: %s. Available docSubTypes: %s",
                            docSubType, docType, docSubTypesList));
                }
                neesDoc.setDocSubType(docSubType);
            }

        }
        int userId = RequestContext.getUserId();
        int nextId = neesDocumentService.getNextId();
        String number = "Document-" + nextId;
        if (!NeesContentType.isSupported(file.getContentType())) {
            throw new NeesBadRequestException(String.format("Unsupported file type. Supported file types: %s", NeesContentType.supportedContentTypes()));
        }

        neesDoc.setNumber(number)
                .setName(file.getOriginalFilename())
                .setMimeContentType(file.getContentType())
                .setCreatedBy(userId)
                .setLastModifiedBy(userId)
                .setCreatedDatetime(LocalDateTime.now(ZoneId.of("UTC")))
                .setModifiedDatetime(LocalDateTime.now(ZoneId.of("UTC")));
        neesDoc = neesDocumentService.save(neesDoc);

        NeesFile neesFile = new NeesFile()
                .setDocId(neesDoc.getId())
                .setFile(file.getBytes());
        neesFileService.save(neesFile);

        UserDocumentRight userDocumentRight = new UserDocumentRight()
                .setUserId(userId)
                .setDocumentId(neesDoc.getId())
                .setDocumentPermission(DocumentPermission.Delete);
        documentRightService.save(userDocumentRight);
        return entityToModel(neesDoc, userDocumentRight, false);
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

    public Map<String, Object> entityToModel(NeesDoc neesDoc, UserDocumentRight userDocumentRight, boolean ignoreThisParam) {

        Map<String, Object> createdBy = new HashMap<>();
        UserAttributes createdByU = userAttributesService.getByUserId(neesDoc.getCreatedBy());
        createdBy.put(NeesObjProps.id.name(), createdByU.getUserId());
        createdBy.put(NeesDocProps.name.name(), createdByU.getFirstName() + " " + createdByU.getLastName());
        Map<String, Object> lastModifiedBy = new HashMap<>();
        UserAttributes lastModifiedByU = userAttributesService.getByUserId(neesDoc.getLastModifiedBy());
        lastModifiedBy.put(NeesObjProps.id.name(), lastModifiedByU.getUserId());
        lastModifiedBy.put(NeesObjProps.name.name(), lastModifiedByU.getFirstName() + " " + lastModifiedByU.getLastName());


        Map<String, Object> document = new HashMap<>();
        document.put(NeesDocProps.id.name(), neesDoc.getId());
        document.put(NeesDocProps.number.name(), neesDoc.getName());
        document.put(NeesDocProps.name.name(), neesDoc.getName());
        document.put(NeesDocProps.description.name(), neesDoc.getDescription());
        document.put(NeesDocProps.docType.name(), neesDoc.getDocType());
        document.put(NeesDocProps.docSubType.name(), neesDoc.getDocSubType());
        document.put(NeesDocProps.objectType.name(), neesDoc.getObjectType());
        document.put(NeesDocProps.objectId.name(), neesDoc.getObjectId());
        document.put(NeesDocProps.permission.name(), userDocumentRight.getDocumentPermission());
//        document.put(NeesDocProps.deleted.name(), neesDoc.isDeleted());
        document.put(NeesDocProps.createdBy.name(), createdBy);
        document.put(NeesDocProps.lastModifiedBy.name(), lastModifiedBy);

        document.put(NeesDocProps.createdDatetime.name(), neesDoc.getCreatedDatetime().format(DateTimeFormatter.ofPattern(DateTimeFormats.DATE_TIME)));
        document.put(NeesDocProps.lastModifiedDatetime.name(), neesDoc.getModifiedDatetime().format(DateTimeFormatter.ofPattern(DateTimeFormats.DATE_TIME)));

        String documentUrl = MvcUriComponentsBuilder.fromController(DocumentController.class)
                .path("document/load")
                .queryParam("id", neesDoc.getId())
                .toUriString();

        String documentDownloadUrl = MvcUriComponentsBuilder.fromController(DocumentController.class)
                .path("document/download")
                .queryParam("id", neesDoc.getId())
                .toUriString();
        document.put(NeesDocProps.docUrl.name(), documentUrl);
        document.put(NeesDocProps.docDownloadUrl.name(), documentDownloadUrl);

        return document;

    }


}
