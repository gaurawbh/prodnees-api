package com.prodnees.core.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.action.DocumentAction;
import com.prodnees.core.action.NeesDocTypeAction;
import com.prodnees.core.action.rel.DocumentRightAction;
import com.prodnees.core.config.constants.APIErrors;
import com.prodnees.core.domain.NeesContentType;
import com.prodnees.core.domain.doc.DocumentPermission;
import com.prodnees.core.domain.doc.NeesDoc;
import com.prodnees.core.domain.doc.NeesFile;
import com.prodnees.core.domain.doc.UserDocumentRight;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.dto.NeesDocDto;
import com.prodnees.core.model.DocumentModel;
import com.prodnees.core.service.doc.NeesFileService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.util.ValidatorUtil;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.prodnees.core.web.response.LocalResponse.configure;

@RestController
@Transactional
public class DocumentController {
    Logger localLogger = LoggerFactory.getLogger(this.getClass());

    private final DocumentAction documentAction;
    private final DocumentRightAction documentRightAction;
    private final NeesDocTypeAction neesDocTypeAction;
    private final NeesFileService neesFileService;


    public DocumentController(DocumentAction documentAction,
                              DocumentRightAction documentRightAction,
                              NeesDocTypeAction neesDocTypeAction,
                              NeesFileService neesFileService) {
        this.documentAction = documentAction;
        this.documentRightAction = documentRightAction;
        this.neesDocTypeAction = neesDocTypeAction;
        this.neesFileService = neesFileService;
    }

    @GetMapping("/doctypes")
    public ResponseEntity<?> getDocTypes(@RequestParam Optional<Integer> id) throws JsonProcessingException {
        if (id.isPresent()) {
            return configure(neesDocTypeAction.getById(id.get()));
        } else {
            return configure(neesDocTypeAction.findAll());
        }
    }

    @PostMapping("/document/upload")
    public ResponseEntity<?> save(@RequestParam(required = false) String docType,
                                  @RequestParam(required = false) String docSubType,
                                  @RequestParam MultipartFile file) {
        Map<String, Object> documentModel = new HashMap<>();
        try {
            documentModel = documentAction.addNew(docType, docSubType, file);
        } catch (IOException e) {
            e.printStackTrace();
            localLogger.error(e.getMessage());
        }
        return configure(documentModel);
    }

    @GetMapping("/document/objects")
    public ResponseEntity<?> getObjectsForDoc(@RequestParam int id) {
        return configure(documentAction.getValidDocObjects(id));
    }

    /**
     * Only {@link NeesDoc} #name can be updated of a Document
     *
     * @param dto
     * @return
     */
    @PutMapping("/document")
    public ResponseEntity<?> update(@Validated @RequestBody NeesDocDto dto) {
        return configure(documentAction.update(dto));
    }

    @PutMapping("/document/reclassify")
    public ResponseEntity<?> reClassify(@RequestParam int id,
                                        @RequestParam String doctype,
                                        @RequestParam(required = false) String docSubtype) throws JsonProcessingException {
        return configure(documentAction.reclassify(id, doctype, docSubtype));
    }

    /**
     * Only {@link ObjectRight#full} can delete a document.
     *
     * @param id
     * @return
     */
    @DeleteMapping("/document")
    public ResponseEntity<?> delete(@RequestParam int id) {

        int userId = RequestContext.getUserId();
        UserDocumentRight userDocumentRight = documentRightAction.findByDocumentIdAndUserId(id, userId)
                .orElseThrow(NeesNotFoundException::new);
        LocalAssert.isTrue(userDocumentRight.getDocumentPermission() == DocumentPermission.Delete, APIErrors.UPDATE_DENIED);
        documentAction.deleteById(id);
        return configure();
    }

    /**
     * Returns a {@link DocumentModel} if id is present.
     * <p>Returns a list of {@link DocumentModel} the current user has access to if id is not present</p>
     *
     * @param id
     * @return
     */
    @GetMapping("/documents")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id) {
        int userId = RequestContext.getUserId();
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
            LocalAssert.isTrue(documentRightAction.existsByDocumentIdAndUserId(integer, userId), APIErrors.OBJECT_NOT_FOUND);
            atomicReference.set(documentAction.getModelById(integer));
        }, () -> atomicReference.set(documentAction.getAllByUserId(userId)));
        return configure(atomicReference.get());
    }


    @GetMapping(value = "/document/load", produces = MediaType.APPLICATION_PDF_VALUE)
    public void loadDocumentFile(@RequestParam int id,
                                 HttpServletResponse servletResponse) {
        int userId = RequestContext.getUserId();
        LocalAssert.isTrue(documentRightAction.existsByDocumentIdAndUserId(id, userId), APIErrors.OBJECT_NOT_FOUND);
        NeesFile neesFile = neesFileService.getByDocId(id);
        servletResponse.setContentType(ValidatorUtil.ifValidStringOrElse(neesFile.getMimeContentType(), MediaType.APPLICATION_PDF_VALUE));
        InputStream inputStream = new ByteArrayInputStream(neesFile.getFile());
        try {
            IOUtils.copy(inputStream, servletResponse.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/document/download")
    public ResponseEntity<?> downloadDocumentFile(@RequestParam int id) {
        int userId = RequestContext.getUserId();
        LocalAssert.isTrue(documentRightAction.existsByDocumentIdAndUserId(id, userId),
                APIErrors.OBJECT_NOT_FOUND);
        NeesDoc neesDoc = documentAction.getById(id);
        NeesFile neesFile = neesFileService.getByDocId(id);
        Resource resource = new ByteArrayResource(neesFile.getFile());
        return ResponseEntity.ok()
                .contentType(NeesContentType.getMediaType(neesFile.getMimeContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + neesDoc.getName() + ".pdf")
                .body(resource);
    }


}
