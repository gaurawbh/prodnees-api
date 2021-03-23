package com.prodnees.filter;

import com.prodnees.config.constants.APIErrors;
import com.prodnees.dao.BlockedJwtDao;
import com.prodnees.service.LoginUserDetailsService;
import com.prodnees.service.UserService;
import com.prodnees.service.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    Logger localLogger = LoggerFactory.getLogger(this.getClass());

    private final LoginUserDetailsService loginUserDetailsService;
    private final UserService userService;
    private final JwtService jwtService;
    private final BlockedJwtDao blockedJwtDao;

    public JWTRequestFilter(LoginUserDetailsService loginUserDetailsService,
                            UserService userService, JwtService jwtService,
                            BlockedJwtDao blockedJwtDao) {
        this.loginUserDetailsService = loginUserDetailsService;
        this.userService = userService;
        this.jwtService = jwtService;
        this.blockedJwtDao = blockedJwtDao;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String username = null;
        String jwt = "";
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
            jwt = authorizationHeader.substring(7);
            try {
                username = jwtService.extractUsername(jwt);
//                localLogger.info(String.format("%s - %s", request.getRequestURI(), request.getMethod()));
                boolean tempPassword = jwtService.hasUsedTempPassword(jwt);
                if (tempPassword) {
                    checkChangePasswordUrl(request, response);
                }

            } catch (Exception i) {
                localLogger.warn(String.format("user wasn't extracted from %s", this.getFilterName()));
            }

        }

        if (userService.existsByEmail(username)
                && !blockedJwtDao.existsByJwt(jwt)
                && jwtService.isValidTail(username, jwt)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = loginUserDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(request, response);

    }

    private void checkChangePasswordUrl(HttpServletRequest request, HttpServletResponse response) {
        if (!request.getRequestURI().equals("/secure/user/temp-password")) {
            response.setHeader("temp_password_unchanged", APIErrors.TEMP_PASSWORD_UNCHANGED.getMessage());
        }
    }
}
