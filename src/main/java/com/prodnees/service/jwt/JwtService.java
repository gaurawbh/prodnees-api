package com.prodnees.service.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;

public interface JwtService{

    String generateToken(UserDetails userDetails);

    String extractUsername(String token);

    int extractUserId(String token);

    String extractUserRole(String token);

    Date extractTokenExpiryDatetime(String token);


}
