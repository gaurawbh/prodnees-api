package com.prodnees.core.action;

import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.core.domain.user.UserAttributes;

public interface ApplicationOwnerAction {

    void deleteApplicationUser(int id);

    UserAttributes updateApplicationRole(int userId, ApplicationRole role);
}
