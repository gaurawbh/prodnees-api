package com.prodnees.auth.jwt.impl;

import com.prodnees.auth.dao.UserDao;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.jwt.ClaimFields;
import com.prodnees.auth.jwt.JwtService;
import com.prodnees.auth.service.JwtTailService;
import com.prodnees.core.config.constants.TimeConstants;
import com.prodnees.core.util.JWTUtil;
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

    private final UserDao userDao;
    private final JwtTailService jwtTailService;

    public JwtServiceImpl(UserDao userDao, JwtTailService jwtTailService) {
        this.userDao = userDao;
        this.jwtTailService = jwtTailService;
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
        userDetails.put(JWTUtil.ClaimFields.COMPANY_ID, user.getCompanyId());
        userDetails.put(ClaimFields.IS_TEMPORARY_PASSWORD, isTempPassword);
        userDetails.put(ClaimFields.TAIL, jwtTailService.generateAndUpdateNextTail(user));
        userDetails.put(JWTUtil.ClaimFields.SCHEMA_INSTANCE, user.getSchemaInstance());
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
    public String extractSchemaInstance(String token) {
        Jws<Claims> jws = extractJws(token);
        return (String) jws.getBody().get(JWTUtil.ClaimFields.SCHEMA_INSTANCE);
    }
    @Override
    public boolean hasUsedTempPassword(String token) {
        Jws<Claims> jws = extractJws(token);
        return (boolean) jws.getBody().get(ClaimFields.IS_TEMPORARY_PASSWORD);
    }

    @Override
    public boolean isValidTail(String username, String jwt) {
        String tail = extractTail(jwt);
        return jwtTailService.isValidTail(username, tail);
    }

    @Override
    public String extractTail(String jwt) {
        return extractAllClaims(jwt).get(ClaimFields.TAIL, String.class);
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
