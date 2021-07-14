/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.core.util;

import org.springframework.util.StringUtils;

import java.util.Objects;

public class LocalStringUtils extends StringUtils {

    public static String emptyIfNull(String arg) {
        return Objects.requireNonNullElse(arg, "");
    }

    public static boolean hasValue(String arg) {
        return arg != null && !arg.isBlank();
    }

    public static String toLowerCamelCase(String originalStr) {
        String alphaNumericStr = originalStr.replaceAll("[^A-Za-z0-9]", " ");
        String[] strArray = alphaNumericStr.split(" ");
        StringBuilder camelCaseBuilder = new StringBuilder();
        for (int i = 0; i < strArray.length; i++) {
            if (i == 0) {
                camelCaseBuilder.append(strArray[i].toLowerCase().strip());
            } else {
                camelCaseBuilder.append(strArray[i].toLowerCase().replaceFirst("[a-z0-9]", strArray[i].substring(0, 1).toUpperCase()));
            }

        }
        return camelCaseBuilder.toString();
    }

}
