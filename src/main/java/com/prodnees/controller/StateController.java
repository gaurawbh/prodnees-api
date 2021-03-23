package com.prodnees.controller;

import com.prodnees.action.EventAction;
import com.prodnees.domain.State;
import com.prodnees.dto.StateDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.service.rels.BatchProductRightService;
import com.prodnees.util.MapperUtil;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure")
@CrossOrigin
public class StateController {
    private final UserValidator userValidator;
    private final BatchProductRightService batchProductRightService;
    private final EventAction eventAction;

    public StateController(UserValidator userValidator,
                           BatchProductRightService batchProductRightService,
                           EventAction eventAction) {
        this.userValidator = userValidator;
        this.batchProductRightService = batchProductRightService;
        this.eventAction = eventAction;
    }

    /**
     * Rules to Adding new States to a Batch Product
     * <p>if lastStateId is not 0, the last State must not be the finalState</p>
     * <p>if nextStateId is not 0,  the next State must not be the initialState</p>
     *
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PostMapping("/state")
    public ResponseEntity<?> save(@Validated @RequestBody StateDto dto,
                                  HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        dto.setId(0);
        State state = MapperUtil.getDozer().map(dto, State.class);
        return configure();
    }

    @GetMapping("/states")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
        }, () -> {
        });
        return configure();
    }

    @PutMapping("/state")
    public ResponseEntity<?> update(@Validated @RequestBody Object obj, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        return LocalResponse.configure();
    }

    @DeleteMapping("/state")
    public ResponseEntity<?> delete(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        return configure();
    }
}
