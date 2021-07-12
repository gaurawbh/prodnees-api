package com.prodnees.core.controller.sys;

import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.core.action.ApplicationOwnerAction;
import com.prodnees.core.action.SysAdminAction;
import com.prodnees.core.dto.user.ApplicationUserDto;
import com.prodnees.core.service.user.UserAttributesService;
import com.prodnees.core.web.response.LocalResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/sys")
public class SysAdminController {

    private final ApplicationOwnerAction applicationOwnerAction;
    private final UserAttributesService userAttributesService;
    private final SysAdminAction sysAdminAction;

    public SysAdminController(ApplicationOwnerAction applicationOwnerAction,
                              UserAttributesService userAttributesService,
                              SysAdminAction sysAdminAction) {
        this.applicationOwnerAction = applicationOwnerAction;
        this.userAttributesService = userAttributesService;
        this.sysAdminAction = sysAdminAction;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(@RequestParam Optional<Integer> id) {
        if (id.isPresent()) {
            return LocalResponse.configure(userAttributesService.getByUserId(id.get()));
        } else {
            return LocalResponse.configure(userAttributesService.findAll());
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> addApplicationUser(@Validated @RequestBody ApplicationUserDto dto) {
        return LocalResponse.configure(sysAdminAction.addApplicationUser(dto));
    }

    @GetMapping("/application-roles")
    public ResponseEntity<?> getAvailableApplicationRoles() {
        return LocalResponse.configure(Arrays.asList(ApplicationRole.values()));
    }


    @PutMapping("/user/disable")
    public ResponseEntity<?> deleteApplicationUser(@RequestParam int id) {
        return LocalResponse.configure(sysAdminAction.disableUser(id));
    }

}
