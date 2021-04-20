package com.prodnees.filter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public interface RequestValidator {

    boolean isValidPhoneNumber(String username);

    boolean isValidEmail(String email);

//    int extractUserId(HttpServletRequest request);

    int extractUserId();

    String extractUserEmail(HttpServletRequest request);
    String extractUserEmail();

    String extractToken(HttpServletRequest request);

    LocalDateTime extractTokenExpiryDatetime(HttpServletRequest request);

    boolean hasUsedTempPassword(HttpServletRequest servletRequest);

}
