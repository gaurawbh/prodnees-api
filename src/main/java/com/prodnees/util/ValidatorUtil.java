package com.prodnees.util;


public abstract class ValidatorUtil {

    public static boolean isNullOrBlank(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }

    public static String ifValidOrElse(String str, String altStr) {
        return isNullOrBlank(str) ? altStr : str;
    }
}
