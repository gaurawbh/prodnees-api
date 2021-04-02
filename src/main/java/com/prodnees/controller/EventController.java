package com.prodnees.controller;

import com.prodnees.action.EventAction;
import com.prodnees.action.rel.BatchProductRightAction;
import com.prodnees.dto.EventDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/secure")
@CrossOrigin
@Transactional
public class EventController {

    private final RequestValidator requestValidator;
    private final EventAction eventAction;
    private final BatchProductRightAction batchProductRightAction;

    public EventController(RequestValidator requestValidator,

                           EventAction eventAction, BatchProductRightAction batchProductRightAction) {
        this.requestValidator = requestValidator;
        this.eventAction = eventAction;
        this.batchProductRightAction = batchProductRightAction;
    }

    @PostMapping("/event")
    public ResponseEntity<?> save(@Validated @RequestBody EventDto dto,
                                  HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        return LocalResponse.configure();
    }

    @GetMapping("/events")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id,
                                 HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
        }, () -> {
        });
        return LocalResponse.configure();
    }

    @PutMapping("/event")
    public ResponseEntity<?> update(@Validated @RequestBody Object obj, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        return LocalResponse.configure();
    }

    @DeleteMapping("/event")
    public ResponseEntity<?> delete(@RequestParam int id,
                                    HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        return LocalResponse.configure();
    }
}
