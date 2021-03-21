package com.prodnees.service.jwt.impl;

import com.prodnees.config.constants.TimeConstants;
import com.prodnees.dao.UserDao;
import com.prodnees.domain.User;
import com.prodnees.service.jwt.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    interface ClaimFields {
        String USER_ID = "userId";
        String ROLE = "role";
        String IS_TEMPORARY_PASSWORD = "tempPassword";
        String ZONE_ID = "zoneId";
        String SECRET_KEY = "eyJ1c2VySWQiOjkwLCJzdWIiOiIxMTEzMzMyMjIiLCJpYXQiOjE1OTQ3MTI1OTEsImV4";
    }

    private final UserDao userDao;

    public JwtServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), false);
    }

    @Override
    public String generateToken(UserDetails userDetails, boolean isTempPassword) {

        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername(), isTempPassword);
    }

    private String createToken(Map<String, Object> claims, String subject, boolean isTempPassword) {

        Map<String, Object> userDetails = new HashMap<>();
        User user = userDao.findByEmail(subject);

        userDetails.put(ClaimFields.USER_ID, user.getId());
        userDetails.put(ClaimFields.ROLE, user.getRole());
        userDetails.put(ClaimFields.IS_TEMPORARY_PASSWORD, isTempPassword);
        int tokenValidity = TimeConstants.ONE_WEEK;
        return Jwts.builder()
                .setClaims(claims)
                .addClaims(userDetails)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenValidity))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(ClaimFields.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public int extractUserId(String token) {
        Jws<Claims> jws = extractJws(token);
        return (int) jws.getBody().get(ClaimFields.USER_ID);
    }

    @Override
    public String extractUserRole(String token) {
        Jws<Claims> jws = extractJws(token);
        return (String) jws.getBody().get(ClaimFields.ROLE);
    }

    @Override
    public Date extractTokenExpiryDatetime(String token) {
        return extractJws(token).getBody().getExpiration();
    }

    @Override
    public boolean hasUsedTempPassword(String token) {
        Jws<Claims> jws = extractJws(token);
        return (boolean) jws.getBody().get(ClaimFields.IS_TEMPORARY_PASSWORD);
    }

    private Jws<Claims> extractJws(String token) {
        String username = extractUsername(token);

        return Jwts.parserBuilder().requireSubject(username)
                .setSigningKey(ClaimFields.SECRET_KEY)
                .build()
                .parseClaimsJws(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }
}
