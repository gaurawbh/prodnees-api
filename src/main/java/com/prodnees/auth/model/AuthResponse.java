package com.prodnees.auth.model;

public class AuthResponse {
    private int userId;
    private boolean isTempPassword;
    private String zoneId;
    private String jwt;

    public int getUserId() {
        return userId;
    }

    public AuthResponse setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public boolean isTempPassword() {
        return isTempPassword;
    }

    public AuthResponse setTempPassword(boolean tempPassword) {
        isTempPassword = tempPassword;
        return this;
    }

    public String getZoneId() {
        return zoneId;
    }

    public AuthResponse setZoneId(String zoneId) {
        this.zoneId = zoneId;
        return this;
    }

    public String getJwt() {
        return jwt;
    }

    public AuthResponse setJwt(String jwt) {
        this.jwt = jwt;
        return this;
    }
}
