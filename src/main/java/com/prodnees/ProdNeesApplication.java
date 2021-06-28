package com.prodnees;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class ProdNeesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdNeesApplication.class, args);
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> index(HttpServletRequest servletRequest) {
        String dateTime = LocalDateTime.now(ZoneId.of("Asia/Kathmandu")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd, hh:mm:ss"));
        Map<String, Object> objectMap = new HashMap<>();
        String apiVersion = "0.1 beta";
        String ip = servletRequest.getHeader("X-Forwarded-For");
        objectMap.put("datetime", dateTime);
        objectMap.put("version", apiVersion);
        objectMap.put("ip", ip);
        return ResponseEntity.ok().body(objectMap);

    }

    @GetMapping("/hola")
    public ResponseEntity<?> greet(){
        return ResponseEntity.ok("Hola! coma estaas!");
    }
}
