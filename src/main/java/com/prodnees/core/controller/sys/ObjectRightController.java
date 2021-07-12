package com.prodnees.core.controller.sys;

import com.prodnees.core.domain.user.NeesObjectRight;
import com.prodnees.core.service.user.NeesObjectRightService;
import com.prodnees.core.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys")
public class ObjectRightController {

    private final NeesObjectRightService objectRightService;

    public ObjectRightController(NeesObjectRightService objectRightService) {
        this.objectRightService = objectRightService;
    }

    @PutMapping("/object-right")
    public ResponseEntity<?> updateObjectRight(@Validated @RequestBody NeesObjectRight objectRight) {
        return LocalResponse.configure(objectRightService.update(objectRight));
    }

    @GetMapping("/object-rights")
    public ResponseEntity<?> getUserObjectRights(@RequestParam int userId) {
        return LocalResponse.configure(objectRightService.getAllByUserId(userId));
    }
}
