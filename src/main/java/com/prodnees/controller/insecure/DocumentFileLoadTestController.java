package com.prodnees.controller.insecure;

import com.prodnees.domain.Document;
import com.prodnees.filter.RequestValidator;
import com.prodnees.service.DocumentService;
import com.prodnees.service.rels.DocumentRightService;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin
@Transactional
public class DocumentFileLoadTestController {

    private final RequestValidator requestValidator;
    private final DocumentService documentService;
    private final DocumentRightService documentRightService;
    Logger localLogger = LoggerFactory.getLogger(this.getClass());

    public DocumentFileLoadTestController(RequestValidator requestValidator, DocumentService documentService, DocumentRightService documentRightService) {
        this.requestValidator = requestValidator;
        this.documentService = documentService;
        this.documentRightService = documentRightService;
    }

    @GetMapping(value = "/document-load-example",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    byte[] loadDocumentFile(@RequestParam int id,
                            HttpServletRequest servletRequest,
                            HttpServletResponse servletResponse) {

        byte[] file = documentService.getById(id).getFile();
        InputStream inputStream = new ByteArrayInputStream(file);
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NeesNotFoundException();
        }
    }

    @GetMapping(value = "/document-download-example")
    public ResponseEntity<Resource> downloadDocumentFile(@RequestParam int id,
                                                         HttpServletRequest servletRequest,
                                                         HttpServletResponse servletResponse) {
        LocalAssert.isTrue(documentService.existsById(id), "document not found");
        Document document = documentService.getById(id);
        Resource resource = new ByteArrayResource(document.getFile());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + document.getName() + ".pdf")
                .body(resource);

    }
}
