package com.prodnees.core.controller.sys;

import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.core.action.ApplicationOwnerAction;
import com.prodnees.core.dto.user.ApplicationUserDto;
import com.prodnees.core.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/sys")
public class AppOwnerController {

    private final ApplicationOwnerAction applicationOwnerAction;

    public AppOwnerController(ApplicationOwnerAction applicationOwnerAction) {
        this.applicationOwnerAction = applicationOwnerAction;
    }

    @PostMapping("/app-user")
    public ResponseEntity<?> addApplicationUser(@Validated @RequestBody ApplicationUserDto dto) {
        return LocalResponse.configure(applicationOwnerAction.addApplicationUser(dto));
    }

    @GetMapping("/application-rights")
    public ResponseEntity<?> getAvailableApplicationRights() {
        return LocalResponse.configure(Arrays.asList(ApplicationRole.values()));
    }

}
