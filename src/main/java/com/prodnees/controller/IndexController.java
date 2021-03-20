package com.prodnees.controller;

import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class IndexController {
    @GetMapping("/")
    public ResponseEntity<?> index() {
        return LocalResponse.configure("index");
    }
}
