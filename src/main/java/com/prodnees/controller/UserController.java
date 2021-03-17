package com.prodnees.controller;

import com.prodnees.action.UserAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.dto.UserRegistrationDto;
import com.prodnees.web.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class UserController {
    private final UserAction userAction;

    public UserController(UserAction userAction) {
        this.userAction = userAction;
    }

    @PostMapping("/user/signup")
    public ResponseEntity<?> save(@Validated @RequestBody UserRegistrationDto dto) {
        Assert.isTrue(!userAction.existsByEmail(dto.getEmail()), APIErrors.EMAIL_TAKEN.getMessage());

        return SuccessResponse.configure(userAction.save(dto));
    }
}
