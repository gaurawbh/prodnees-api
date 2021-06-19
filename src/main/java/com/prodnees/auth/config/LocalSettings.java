/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.config;

public interface LocalSettings {
    interface Datasource {
        String URL = "spring.datasource.url";
        String USERNAME = "spring.datasource.username";
        String PASSWORD = "spring.datasource.password";
        String DRIVER = "spring.datasource.driver-class-name";
    }

    interface JPA {
        String DB_PLATFORM = "spring.jpa.database-platform";
    }
}
