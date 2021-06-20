package com.prodnees.auth.action;

import com.prodnees.auth.domain.User;
import com.prodnees.dto.user.SignupDto;
import com.prodnees.model.user.AssociateModel;
import com.prodnees.model.user.UserModel;

import java.util.List;

public interface UserAction {
    boolean existsById(int id);

    boolean existsByEmail(String email);

    UserModel save(SignupDto dto);

    UserModel save(User user);

    UserModel getModelById(int id);

    AssociateModel getAssociateById(int id);

    AssociateModel getAssociateByEmail(String email);

    UserModel getModelByEmail(String email);

    User getById(int id);

    User getByEmail(String email);

    List<AssociateModel> getAllAssociates(int adminId);


}
