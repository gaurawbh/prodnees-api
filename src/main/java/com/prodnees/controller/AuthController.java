package com.prodnees.controller;

import com.prodnees.dto.AuthDto;
import com.prodnees.web.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/secure")
@CrossOrigin
public class AuthController {

    @PostMapping("/authenticate")
    public ResponseEntity<?> save(@Validated @RequestBody AuthDto authDto,
                                  HttpServletRequest servletRequest) {
        return SuccessResponse.configure();
    }
}
