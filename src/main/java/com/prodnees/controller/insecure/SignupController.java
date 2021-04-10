package com.prodnees.controller.insecure;

import com.prodnees.action.AuthAction;
import com.prodnees.action.UserAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.dto.user.UserRegistrationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.prodnees.config.constants.APIErrors.USER_NOT_FOUND;
import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@CrossOrigin
@Transactional
public class SignupController {
    private final UserAction userAction;
    private final AuthAction authAction;

    public SignupController(UserAction userAction,
                            AuthAction authAction) {
        this.userAction = userAction;
        this.authAction = authAction;
    }

    @GetMapping("/email-exists")
    public boolean existsByEmail(@RequestParam String email){
        return userAction.existsByEmail(email);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<?> save(@Validated @RequestBody UserRegistrationDto dto) {
        Assert.isTrue(!userAction.existsByEmail(dto.getEmail()), APIErrors.EMAIL_TAKEN.getMessage());
        return configure(userAction.save(dto));
    }

    @PostMapping("/user/forgot-password")
    public ResponseEntity<?> save(@RequestParam String email) {
        Assert.isTrue(userAction.existsByEmail(email), USER_NOT_FOUND.getMessage());
        authAction.processForgotPassword(email);
        return configure("forgot password request processed successfully");
    }
}
