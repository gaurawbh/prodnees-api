package com.prodnees.auth.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.prodnees.auth.domain.User;

public interface TenantService {

    void createNewSchema(User user) throws JsonProcessingException;
}
