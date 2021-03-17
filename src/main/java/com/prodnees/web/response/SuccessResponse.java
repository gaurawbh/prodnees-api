package com.prodnees.web.response;

import com.prodnees.config.constants.APIErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SuccessResponse {
    public static ResponseEntity<?> configure(String message, Object object) {
        return new ResponseEntity<>(
                new GlobalResponse(HttpStatus.OK, false, message, APIErrors.SUCCESS.getCode(), object),
                HttpStatus.OK);
    }

    public static ResponseEntity<?> configure() {
        return new ResponseEntity<>(
                new GlobalResponse(HttpStatus.OK, false, APIErrors.SUCCESS.getMessage(), APIErrors.SUCCESS.getCode()),
                HttpStatus.OK);
    }

    public static ResponseEntity<?> configure(String message, int code, Object object) {

        return new ResponseEntity<>(
                new GlobalResponse(HttpStatus.OK, false, message, code, object),
                HttpStatus.OK);

    }

    public static ResponseEntity<?> configure(Object object) {

        return new ResponseEntity<>(
                new GlobalResponse(HttpStatus.OK, false, APIErrors.SUCCESS.getMessage(), APIErrors.SUCCESS.getCode(), object),
                HttpStatus.OK);

    }

    public static ResponseEntity<?> configure(APIErrors constants, Object object) {
        return new ResponseEntity<>(
                new GlobalResponse(HttpStatus.OK, false, constants.getMessage(), constants.getCode(), object),
                HttpStatus.OK);

    }
}
