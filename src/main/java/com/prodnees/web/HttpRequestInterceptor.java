package com.prodnees.web;

import com.prodnees.auth.config.tenancy.TenantContext;
import com.prodnees.auth.util.TenantUtil;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.web.exception.NeesForbiddenException;
import com.prodnees.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class HttpRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request,
                             HttpServletResponse response,
                             @Nonnull Object handler) {

        if (response.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) {
            throw new NeesNotFoundException(APIErrors.INVALID_JWT_TOKEN);
        }
        if (response.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
            throw new NeesForbiddenException(APIErrors.PROTECTED_URL);
        }
        if (response.getHeader("temp_password_unchanged") != null) {
            throw new NeesForbiddenException(APIErrors.TEMP_PASSWORD_UNCHANGED);
        }
        String schema = TenantContext.getCurrentTenant();
        if (schema == null) {
            TenantContext.setCurrentTenant(TenantUtil.MASTER_SCHEMA);
        }
        return true;
    }
}