package com.prodnees.filter;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public interface UserValidator {

    boolean isValidPhoneNumber(String username);

    int extractUserId(HttpServletRequest request);

    String extractUserEmail(HttpServletRequest request);

    String extractToken(HttpServletRequest request);

    LocalDateTime extractTokenExpiryDatetime(HttpServletRequest request);

    String extractUserRole(HttpServletRequest servletRequest);

    boolean isValidEmail(String email);

    boolean isSecureRequest(String url);

}
