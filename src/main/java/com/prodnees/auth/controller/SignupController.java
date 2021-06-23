package com.prodnees.auth.controller;

import com.prodnees.auth.action.AuthAction;
import com.prodnees.auth.action.UserAction;
import com.prodnees.auth.service.SignupService;
import com.prodnees.core.dto.user.SignupDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.prodnees.core.config.constants.APIErrors.USER_NOT_FOUND;
import static com.prodnees.core.web.response.LocalResponse.configure;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class SignupController {
    private final UserAction userAction;
    private final AuthAction authAction;
    private final SignupService signupService;

    public SignupController(UserAction userAction,
                            AuthAction authAction, SignupService signupService) {
        this.userAction = userAction;
        this.authAction = authAction;
        this.signupService = signupService;
    }

    @GetMapping("/email-exists")
    public boolean existsByEmail(@RequestParam String email) {
        return userAction.existsByEmail(email);
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<?> signup(@Validated @RequestBody SignupDto dto) {
        return configure(signupService.signup(dto));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> save(@RequestParam String email) {
        Assert.isTrue(userAction.existsByEmail(email), USER_NOT_FOUND.getMessage());
        authAction.processForgotPassword(email);
        return configure("forgot password request processed successfully");
    }
}
