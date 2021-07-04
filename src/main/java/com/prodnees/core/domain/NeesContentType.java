package com.prodnees.core.domain;

import org.springframework.http.MediaType;

import java.io.Serializable;
import java.util.List;

public class NeesContentType implements Serializable {

    private static final long serialVersionUID = 2069937152339670221L;
    public static final String IMAGE_PNG_VALUE = MediaType.IMAGE_PNG_VALUE;
    public static final String IMAGE_JPEG_VALUE = MediaType.IMAGE_JPEG_VALUE;
    public static final String APPLICATION_PDF_VALUE = MediaType.APPLICATION_PDF_VALUE;

    public static List<String> supportedContentTypes() {
        return List.of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, APPLICATION_PDF_VALUE);
    }

    public static boolean isSupported(String mimeContentType) {
        return supportedContentTypes().contains(mimeContentType);

    }

}
