/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.filter;

import com.prodnees.domain.user.User;
import com.prodnees.util.JWTUtil;
import com.prodnees.util.LocalAssert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;


public class RequestContext {

    public static int getUserId() {
        Jws<Claims> jws = extractJws();
        return (int) jws.getBody().get(JWTUtil.ClaimFields.USER_ID);
    }

    public static int getCompanyId() {
        Jws<Claims> jws = extractJws();
        return (int) jws.getBody().get(JWTUtil.ClaimFields.COMPANY_ID);
    }

    public static Boolean isTempPassword() {
        Jws<Claims> jws = extractJws();
        return jws.getBody().get(JWTUtil.ClaimFields.IS_TEMPORARY_PASSWORD, Boolean.class);
    }

    public static String getZoneId() {
        Jws<Claims> jws = extractJws();
        return (String) jws.getBody().get(JWTUtil.ClaimFields.ZONE_ID);
    }

    public static String getUsername() {
        Jws<Claims> jws = extractJws();
        return extractClaim(Claims::getSubject);
    }

    private static Jws<Claims> extractJws() {
        String token = extractToken();
        String username = extractClaim(Claims::getSubject);
        return Jwts.parserBuilder().requireSubject(username)
                .setSigningKey(JWTUtil.ClaimFields.SECRET_KEY)
                .build()
                .parseClaimsJws(token);
    }

    private static <T> T extractClaim(Function<Claims, T> claimsResolver) {
        String token = extractToken();
        final Claims claims = Jwts.parserBuilder().setSigningKey(JWTUtil.getKey()).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

    public static String extractToken() {
        HttpServletRequest request = getSecureServletRequest();
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader.substring(7);
    }

    private static HttpServletRequest getSecureServletRequest() {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        LocalAssert.isTrue(servletRequest.getHeader("Authorization").contains("Bearer"), "trying to access secure servlet request without authenticating");
        return servletRequest;
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static boolean isValidSuperAdminEmail(String email) {
        return isValidEmail(email) && email.endsWith("@neesum.com");
    }

}
