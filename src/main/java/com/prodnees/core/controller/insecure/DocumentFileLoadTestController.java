package com.prodnees.core.controller.insecure;

import com.prodnees.core.domain.doc.NeesFile;
import com.prodnees.core.service.doc.NeesFileService;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.core.web.exception.NeesNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class DocumentFileLoadTestController {

    private final NeesFileService neesFileService;

    public DocumentFileLoadTestController(NeesFileService neesFileService) {
        this.neesFileService = neesFileService;
    }


    @GetMapping(value = "/document-load-example",
            produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody
    byte[] loadDocumentFile(@RequestParam int id) {

        byte[] file = neesFileService.getByDocId(id).getFile();
        InputStream inputStream = new ByteArrayInputStream(file);
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            throw new NeesNotFoundException();
        }
    }

    @GetMapping(value = "/document-download-example")
    public ResponseEntity<Resource> downloadDocumentFile(@RequestParam int id) {
        LocalAssert.isTrue(neesFileService.existsByDocId(id), "document not found");
        NeesFile neesDoc = neesFileService.getByDocId(id);
        Resource resource = new ByteArrayResource(neesDoc.getFile());
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + neesDoc.getFileName() + ".pdf")
                .body(resource);

    }
}
