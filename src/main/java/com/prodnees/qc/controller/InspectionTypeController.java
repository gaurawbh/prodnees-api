package com.prodnees.qc.controller;

import com.prodnees.qc.action.InspectionTypeAction;
import com.prodnees.qc.dto.InspectionTypeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.prodnees.core.web.response.LocalResponse.configure;

@RestController
@CrossOrigin
@Transactional
public class InspectionTypeController {
    private final InspectionTypeAction inspectionTypeAction;

    public InspectionTypeController(InspectionTypeAction inspectionTypeAction) {
        this.inspectionTypeAction = inspectionTypeAction;
    }

    @PostMapping("/inspection-type")
    public ResponseEntity<?> addNew(@Validated @RequestBody InspectionTypeDto dto) {
        return configure(inspectionTypeAction.addNew(dto));
    }

    @GetMapping("/inspection-types")
    public ResponseEntity<?> get(@RequestParam(required = false) Optional<Integer> id) {
        if (id.isPresent()) {
            return configure(inspectionTypeAction.getById(id.get()));
        } else {
            return configure(inspectionTypeAction.findAll());
        }
    }

    @PutMapping("/inspection-type")
    public ResponseEntity<?> update(@Validated @RequestBody InspectionTypeDto dto) {
        return configure(inspectionTypeAction.update(dto));
    }

    @DeleteMapping("/inspection-type")
    public ResponseEntity<?> delete(@RequestParam int id) {
        inspectionTypeAction.deleteById(id);
        return configure(String.format("Successfully deleted Inspection Type with id: %d", id));
    }
}
