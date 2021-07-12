package com.prodnees.core.action;

import com.prodnees.auth.domain.User;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.dto.user.ApplicationUserDto;

public interface SysAdminAction {
    User disableUser(int id);


    UserAttributes addApplicationUser(ApplicationUserDto dto);


}
