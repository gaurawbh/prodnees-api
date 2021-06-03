/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.util;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public abstract class JWTUtil {

    public interface Headers {
        String BEARER_PREFIX = "Bearer ";
        String AUTHORIZATION = "Authorization";
    }

    public static Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(ClaimFields.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public interface ClaimFields {
        String USER_ID = "userId";
        String ROLE = "role";
        String COMPANY_ID = "companyId";
        String IS_TEMPORARY_PASSWORD = "isTempPassword";
        String ZONE_ID = "zoneId";
        String TAIL = "tail";
        String SECRET_KEY = "eyJ1c2VySWQiOjkwLCJzdWIiOiIxMTEzMzMyMjIiLCJpYXQiOjE1OTQ3MTI1OTEsImV4";
        String AUTHORIZATION_HEADER = "authorization";
        String SCHEMA_INSTANCE = "schemaInstance";

    }
}
