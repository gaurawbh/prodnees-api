package com.prodnees.auth.jwt;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

public interface JwtService {

    String generateToken(UserDetails userDetails);

    String generateToken(UserDetails userDetails, boolean isTempPassword);

    String extractUsername(String token);

    int extractUserId(String token);

    String extractUserRole(String token);

    Date extractTokenExpiryDatetime(String token);
    String extractSchemaInstance(String token);

    boolean hasUsedTempPassword(String token);

    boolean isValidTail(String username, String jwt);

    String extractTail(String jwt);
}
