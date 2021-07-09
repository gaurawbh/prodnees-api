package com.prodnees.core.action;

import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.dto.user.ApplicationUserDto;

public interface ApplicationOwnerAction {

    UserAttributes addApplicationUser(ApplicationUserDto dto);
}
