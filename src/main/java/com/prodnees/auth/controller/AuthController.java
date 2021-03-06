package com.prodnees.auth.controller;

import com.prodnees.auth.action.AuthAction;
import com.prodnees.auth.action.UserAction;
import com.prodnees.auth.dao.ForgotPasswordInfoDao;
import com.prodnees.auth.dao.TempPasswordInfoDao;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.dto.AuthDto;
import com.prodnees.auth.jwt.JwtService;
import com.prodnees.auth.model.AuthResponse;
import com.prodnees.auth.service.LoginUserDetailsService;
import com.prodnees.core.web.exception.NeesBadCredentialException;
import com.prodnees.core.web.exception.NeesForbiddenException;
import com.prodnees.core.web.response.LocalResponse;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.prodnees.core.config.constants.APIErrors.EMAIL_NOT_FOUND;
import static com.prodnees.core.config.constants.APIErrors.USER_NOT_ENABLED;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TempPasswordInfoDao tempPasswordInfoDao;
    private final UserAction userAction;
    private final LoginUserDetailsService loginUserDetailsService;
    private final ForgotPasswordInfoDao forgotPasswordInfoDao;
    private final AuthAction authAction;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,

                          TempPasswordInfoDao tempPasswordInfoDao,
                          UserAction userAction,
                          LoginUserDetailsService loginUserDetailsService,
                          ForgotPasswordInfoDao forgotPasswordInfoDao,
                          AuthAction authAction, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.tempPasswordInfoDao = tempPasswordInfoDao;
        this.userAction = userAction;
        this.loginUserDetailsService = loginUserDetailsService;
        this.forgotPasswordInfoDao = forgotPasswordInfoDao;
        this.authAction = authAction;
        this.jwtService = jwtService;
    }


    @PostMapping("/authenticate")
    public ResponseEntity<?> save(@Validated @RequestBody AuthDto authDto) {

        Assert.isTrue(userAction.existsByEmail(authDto.getEmail()), String.format(EMAIL_NOT_FOUND.getMessage(), authDto.getEmail()));
        User user = userAction.getByEmail(authDto.getEmail());
        final UserDetails userDetails = loginUserDetailsService.loadUserByUsername(authDto.getEmail());
        boolean isTempPassword = tempPasswordInfoDao.existsByEmail(authDto.getEmail());
        boolean isForgotPassword = forgotPasswordInfoDao.existsByEmail(authDto.getEmail());
        AuthResponse authResponse = new AuthResponse()
                .setUserId(user.getId())
                .setRole(user.getRole())
                .setZoneId("UTC");
        if (isForgotPassword && authAction.authenticateWithForgotPasswordCredentials(authDto.getEmail(), authDto.getPassword())) {
            final String jwt = jwtService.generateToken(userDetails, true);
            authResponse.setJwt(jwt)
                    .setTempPassword(true);
        } else {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword()));
            } catch (BadCredentialsException badCredentialsException) {
                throw new NeesBadCredentialException();
            } catch (DisabledException disabledException) {
                throw new NeesForbiddenException(USER_NOT_ENABLED);
            }
            final String jwt = jwtService.generateToken(userDetails, isTempPassword);
            authResponse.setJwt(jwt)
                    .setTempPassword(isTempPassword);
        }

        return LocalResponse.configure(authResponse);
    }
}
