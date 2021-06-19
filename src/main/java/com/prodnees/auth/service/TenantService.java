package com.prodnees.auth.service;


import com.prodnees.auth.domain.User;

public interface TenantService {

    void createNewSchema(User user);
}
