package com.prodnees;

import com.prodnees.web.response.LocalResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ProdNeesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdNeesApplication.class, args);
    }

    @GetMapping("/")
    public ResponseEntity<?> index() {
        return LocalResponse.configure("looks like finally its working");
    }

}
