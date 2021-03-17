package com.prodnees.web;

import com.prodnees.config.constants.APIErrors;
import com.prodnees.web.exception.NeesForbiddenException;
import com.prodnees.web.exception.NeesNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class HttpRequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (response.getStatus() == HttpServletResponse.SC_UNAUTHORIZED) {
            throw new NeesNotFoundException(APIErrors.INVALID_JWT_TOKEN);
        }
        if (response.getStatus() == HttpServletResponse.SC_FORBIDDEN) {
            throw new NeesForbiddenException(APIErrors.PROTECTED_URL);
        }

        return true;
    }
}