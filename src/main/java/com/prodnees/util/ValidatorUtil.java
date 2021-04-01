package com.prodnees.util;


import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class ValidatorUtil {
    private ValidatorUtil() {
    }

    public static boolean isNullOrBlank(String str) {
        return str == null || str.isEmpty() || str.isBlank();
    }

    public static String ifValidStringOrElse(String str, String altStr) {
        return isNullOrBlank(str) ? altStr : str;
    }

    public static Integer ifValidIntegerOrElse(Integer integer, @Positive(message = "altInteger must be a positive number") Integer altInteger) {
        return integer > 0 ? integer : altInteger;
    }

    public static LocalDate ifValidLocalDateOrElse(LocalDate localDate, LocalDate altLocalDate) {
        return localDate == null ? altLocalDate : localDate;
    }

    public static LocalTime ifValidLocalTimeOrElse(LocalTime localTime, LocalTime altLocalTime) {
        return localTime == null ? altLocalTime : localTime;
    }
}
