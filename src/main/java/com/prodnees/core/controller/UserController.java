package com.prodnees.core.controller;

import com.prodnees.auth.filter.RequestContext;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.dto.user.UserAttributesDto;
import com.prodnees.core.service.user.UserAttributesService;
import com.prodnees.core.util.ValidatorUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.prodnees.core.web.response.LocalResponse.configure;

@RestController
@RequestMapping("/secure/")
@Transactional
public class UserController {
    private final UserAttributesService userAttributesService;

    public UserController(UserAttributesService userAttributesService) {
        this.userAttributesService = userAttributesService;
    }

    /**
     * @return
     */
    @GetMapping("/user")
    public ResponseEntity<?> getUser() {
        int userId = RequestContext.getUserId();
        return configure(userAttributesService.getByUserId(userId));
    }

    @PutMapping("/user")
    public ResponseEntity<?> update(@Validated @RequestBody UserAttributesDto dto) {
        int userId = RequestContext.getUserId();
        UserAttributes userAttributes = userAttributesService.getByUserId(userId);
        userAttributes.setFirstName(dto.getFirstName())
                .setLastName(dto.getLastName())
                .setAddress(ValidatorUtil.ifValidStringOrElse(dto.getAddress(), userAttributes.getAddress()))
                .setPhoneNumber(ValidatorUtil.ifValidStringOrElse(dto.getPhoneNumber(), userAttributes.getPhoneNumber()));

        return configure(userAttributesService.save(userAttributes));
    }
}
