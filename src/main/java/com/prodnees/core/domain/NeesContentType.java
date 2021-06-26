package com.prodnees.core.domain;

import java.io.Serializable;
import java.util.List;

public class NeesContentType implements Serializable {

    private static final long serialVersionUID = 2069937152339670221L;
    public static final String ALL_VALUE = "*/*";
    public static final String IMAGE_PNG_VALUE = "image/png";
    public static final String IMAGE_JPEG_VALUE = "image/jpeg";
    public static final String APPLICATION_PDF_VALUE = "application/pdf";

    public static List<String> supportedContentTypes() {
        return List.of(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, APPLICATION_PDF_VALUE);
    }

}
