package com.prodnees.core.controller.owner;

import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.core.action.ApplicationOwnerAction;
import com.prodnees.core.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.prodnees.core.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/ownr")
public class AppOwnerController {
    private final ApplicationOwnerAction applicationOwnerAction;

    public AppOwnerController(ApplicationOwnerAction applicationOwnerAction) {
        this.applicationOwnerAction = applicationOwnerAction;
    }
    @GetMapping("/url-test")
    public ResponseEntity<?> testRole(){
        return configure("this is owner url");
    }

    @PutMapping("/user-role")
    public ResponseEntity<?> updateApplicationRole(@RequestParam int userId, @RequestParam ApplicationRole role) {
        return LocalResponse.configure(applicationOwnerAction.updateApplicationRole(userId, role));
    }

}
