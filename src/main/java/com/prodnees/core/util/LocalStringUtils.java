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

}
