package com.prodnees.controller;

import com.prodnees.action.EventAction;
import com.prodnees.domain.State;
import com.prodnees.dto.StateDto;
import com.prodnees.filter.UserValidator;
import com.prodnees.service.rels.BatchProductRightsService;
import com.prodnees.util.MapperUtil;
import com.prodnees.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
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
import static com.prodnees.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure")
@CrossOrigin
public class StateController {
    private final UserValidator userValidator;
    private final BatchProductRightsService batchProductRightsService;
    private final EventAction eventAction;

    public StateController(UserValidator userValidator,
                           BatchProductRightsService batchProductRightsService,
                           EventAction eventAction) {
        this.userValidator = userValidator;
        this.batchProductRightsService = batchProductRightsService;
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

    @GetMapping("/")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
        }, () -> {
        });
        return configure();
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@Validated @RequestBody Object obj, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        return LocalResponse.configure();
    }

    @DeleteMapping("/")
    public ResponseEntity<?> delete(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = userValidator.extractUserId(servletRequest);
        return configure();
    }
}
