package com.prodnees.controller;

import com.prodnees.action.DocumentAction;
import com.prodnees.action.rel.DocumentRightAction;
import com.prodnees.auth.filter.RequestContext;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.Document;
import com.prodnees.domain.enums.ObjectRight;
import com.prodnees.domain.rels.DocumentRight;
import com.prodnees.dto.DocumentDto;
import com.prodnees.model.DocumentModel;
import com.prodnees.util.LocalAssert;
import com.prodnees.web.exception.NeesNotFoundException;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure/")
@CrossOrigin
@Transactional
public class DocumentController {

    private final DocumentAction documentAction;
    private final DocumentRightAction documentRightAction;
    Logger localLogger = LoggerFactory.getLogger(this.getClass());

    public DocumentController(DocumentAction documentAction,
                              DocumentRightAction documentRightAction) {
        this.documentAction = documentAction;
        this.documentRightAction = documentRightAction;
    }

    @PostMapping("/document")
    public ResponseEntity<?> save(@RequestParam MultipartFile file,
                                  String name) {
        int userId = RequestContext.getUserId();
        DocumentModel documentModel = new DocumentModel();
        try {
            Document document = new Document()
                    .setName(name)
                    .setFile(file.getBytes());
            documentModel = documentAction.save(document);
            DocumentRight documentRight = new DocumentRight()
                    .setUserId(userId)
                    .setDocumentId(documentModel.getId())
                    .setDocumentRightsType(ObjectRight.OWNER);
            documentRightAction.save(documentRight);
        } catch (IOException e) {
            e.printStackTrace();
            localLogger.error(e.getMessage());
        }
        return configure(documentModel);
    }

    /**
     * Only {@link Document} #name can be updated of a Document
     *
     * @param dto
     * @return
     */
    @PutMapping("/document")
    public ResponseEntity<?> update(@Validated @RequestBody DocumentDto dto) {
        int userId = RequestContext.getUserId();
        DocumentRight documentRight = documentRightAction.findByDocumentIdAndUserId(dto.getId(), userId).orElseThrow(NeesNotFoundException::new);
        LocalAssert.isTrue(documentRightAction.hasEditRights(documentRight), APIErrors.UPDATE_DENIED);
        Document document = documentAction.getById(dto.getId()).setName(dto.getName());
        return configure(document);
    }

    /**
     * Only {@link ObjectRight#OWNER} can delete a document.
     * <p>A document cannot be deleted if it is referenced by an {@link com.prodnees.domain.stage.StageApprovalDocument}</p>
     *
     * @param id
     * @return
     */
    @DeleteMapping("/document")
    public ResponseEntity<?> delete(@RequestParam int id) {

        int userId = RequestContext.getUserId();
        DocumentRight documentRight = documentRightAction.findByDocumentIdAndUserId(id, userId)
                .orElseThrow(NeesNotFoundException::new);
        LocalAssert.isTrue(documentRight.getDocumentRightsType() == ObjectRight.OWNER, APIErrors.UPDATE_DENIED);
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
        byte[] file = documentAction.getById(id).getFile();
        InputStream inputStream = new ByteArrayInputStream(file);
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
        Document document = documentAction.getById(id);

        Resource resource = new ByteArrayResource(document.getFile());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + document.getName() + ".pdf")
                .body(resource);
    }
}
