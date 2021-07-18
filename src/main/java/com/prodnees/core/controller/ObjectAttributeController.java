package com.prodnees.core.controller;

import com.prodnees.core.dto.ObjectAttributeDto;
import com.prodnees.core.web.response.LocalResponse;
import com.prodnees.shelf.service.ObjectAttributeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ObjectAttributeController {

    private final ObjectAttributeService objectAttributeService;

    public ObjectAttributeController(ObjectAttributeService objectAttributeService) {
        this.objectAttributeService = objectAttributeService;
    }

    @PostMapping("/object-attribute")
    public ResponseEntity<?> addObjectAttribute(@Validated @RequestBody ObjectAttributeDto dto) {
        return LocalResponse.configure(objectAttributeService.addObjectAttribute(dto));
    }
}
