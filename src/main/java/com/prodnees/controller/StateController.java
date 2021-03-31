package com.prodnees.controller;

import com.prodnees.action.EventAction;
import com.prodnees.action.StateAction;
import com.prodnees.config.constants.APIErrors;
import com.prodnees.domain.State;
import com.prodnees.dto.StateDto;
import com.prodnees.filter.RequestValidator;
import com.prodnees.service.rels.BatchProductRightService;
import com.prodnees.util.LocalAssert;
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
    private final RequestValidator requestValidator;
    private final BatchProductRightService batchProductRightService;
    private final EventAction eventAction;
    private final StateAction stateAction;

    public StateController(RequestValidator requestValidator,
                           BatchProductRightService batchProductRightService,
                           EventAction eventAction,
                           StateAction stateAction) {
        this.requestValidator = requestValidator;
        this.batchProductRightService = batchProductRightService;
        this.eventAction = eventAction;
        this.stateAction = stateAction;
    }

    /**
     * Rules to Adding new States to a Batch Product
     * <p>if lastStateId is not 0, the last State must not be the finalState</p>
     * <p>if nextStateId is not 0,  the next State must not be the initialState</p>
     *
     * @param dto
     * @param servletRequest
     * @return
     */
    @PostMapping("/state")
    public ResponseEntity<?> save(@Validated @RequestBody StateDto dto,
                                  HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        LocalAssert.isTrue(batchProductRightService.hasBatchProductEditorRights(dto.getBatchProductId(), userId),
                APIErrors.BATCH_PRODUCT_NOT_FOUND);
        if (dto.isFinalState()) {
            LocalAssert.isTrue(dto.getNextStateId() <= 0, "final state cannot have nextStateId");
        }
        if (dto.isInitialState()) {
            LocalAssert.isTrue(dto.getLastStateId() <= 0, "initial state cannot have lastStateId");
        }
        dto.setId(0);
        State state = MapperUtil.getDozer().map(dto, State.class);
        return configure(stateAction.save(state));
    }

    @GetMapping("/states")
    public ResponseEntity<?> get(@RequestParam Optional<Integer> id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        AtomicReference<Object> atomicReference = new AtomicReference<>();
        id.ifPresentOrElse(integer -> {
        }, () -> {
        });
        return configure();
    }

    @PutMapping("/state")
    public ResponseEntity<?> update(@Validated @RequestBody Object obj, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        return LocalResponse.configure();
    }

    @DeleteMapping("/state")
    public ResponseEntity<?> delete(@RequestParam int id, HttpServletRequest servletRequest) {
        int userId = requestValidator.extractUserId(servletRequest);
        return configure();
    }
}
