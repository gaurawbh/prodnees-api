package com.prodnees.core.controller.insecure;

import com.prodnees.core.service.NeesDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentFileLoadTestController {

    private final NeesDocumentService neesDocumentService;
    Logger localLogger = LoggerFactory.getLogger(this.getClass());

    public DocumentFileLoadTestController(NeesDocumentService neesDocumentService) {
        this.neesDocumentService = neesDocumentService;
    }

//    @GetMapping(value = "/document-load-example",
//            produces = MediaType.APPLICATION_PDF_VALUE)
//    public @ResponseBody
//    byte[] loadDocumentFile(@RequestParam int id) {
//
//        byte[] file = neesDocumentService.getById(id).getFile();
//        InputStream inputStream = new ByteArrayInputStream(file);
//        try {
//            return IOUtils.toByteArray(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new NeesNotFoundException();
//        }
//    }
//
//    @GetMapping(value = "/document-download-example")
//    public ResponseEntity<Resource> downloadDocumentFile(@RequestParam int id) {
//        LocalAssert.isTrue(neesDocumentService.existsById(id), "document not found");
//        NeesDoc neesDoc = neesDocumentService.getById(id);
//        Resource resource = new ByteArrayResource(neesDoc.getFile());
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_PDF)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + neesDoc.getName() + ".pdf")
//                .body(resource);
//
//    }
}
