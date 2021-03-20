package com.prodnees.controller.insecure;

import com.prodnees.action.UserAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.dto.UserRegistrationDto;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserSignupController {
    private final UserAction userAction;

    public UserSignupController(UserAction userAction) {
        this.userAction = userAction;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<?> save(@Validated @RequestBody UserRegistrationDto dto) {
        Assert.isTrue(!userAction.existsByEmail(dto.getEmail()), APIErrors.EMAIL_TAKEN.getMessage());

        return LocalResponse.configure(userAction.save(dto));
    }
}
