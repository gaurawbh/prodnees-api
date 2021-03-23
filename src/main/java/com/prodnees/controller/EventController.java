package com.prodnees.controller;

import com.prodnees.action.EventAction;
import com.prodnees.action.rel.BatchProductRightAction;
import com.prodnees.filter.UserValidator;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/secure")
@CrossOrigin
public class EventController {

    private final UserValidator userValidator;
    private final EventAction eventAction;
    private final BatchProductRightAction batchProductRightAction;

    public EventController(UserValidator userValidator,

                           EventAction eventAction, BatchProductRightAction batchProductRightAction) {
        this.userValidator = userValidator;
        this.eventAction = eventAction;
        this.batchProductRightAction = batchProductRightAction;
    }

    @PostMapping("/state")
    public ResponseEntity<?> save(@Validated @RequestBody Object dto,
                                  HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        return LocalResponse.configure();
    }

    @GetMapping("/states")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
        }, () -> {
        });
        return LocalResponse.configure();
    }

    @PutMapping("/state")
    public ResponseEntity<?> update(@Validated @RequestBody Object obj, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        return LocalResponse.configure();
    }

    @DeleteMapping("/state")
    public ResponseEntity<?> delete(@RequestParam int id,
                                    HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        return LocalResponse.configure();
    }
}
