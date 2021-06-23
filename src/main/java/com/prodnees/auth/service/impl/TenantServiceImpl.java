/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.service.impl;

import com.prodnees.auth.config.SessionFactoryConfig;
import com.prodnees.auth.domain.ApplicationRight;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.domain.UserRole;
import com.prodnees.auth.service.TenantService;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.util.LocalAssert;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static com.prodnees.auth.util.TenantUtil.VALID_SCHEMA_PATTERN;


@Service
@Transactional
public class TenantServiceImpl implements TenantService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DataSource dataSource;
    private final SessionFactoryConfig sessionFactoryConfig;

    public TenantServiceImpl(DataSource dataSource,
                             SessionFactoryConfig sessionFactoryConfig) {
        this.dataSource = dataSource;
        this.sessionFactoryConfig = sessionFactoryConfig;
    }

    @Override
    public void createNewSchema(User user) {
        String schema = user.getSchemaInstance();
        LocalAssert.isTrue(schema.matches(VALID_SCHEMA_PATTERN), "invalid schema name");
        MigrateResult re = initNewSchema(schema);
        if (re.migrationsExecuted > 0) {
            logger.info("Schema {} was successfully created", schema);
        }
        Session session = sessionFactoryConfig.getCurrentSession(schema).openSession();

        Transaction tx = session.beginTransaction();

        UserAttributes employee = new UserAttributes()
                .setUserId(user.getId())
                .setApplicationRight(ApplicationRight.owner)
                .setEmail(user.getEmail())
                .setRole(UserRole.owner)
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName());
        session.save(employee);
        tx.commit();
        session.close();
    }

    public MigrateResult initNewSchema(String schema) {
        Flyway flyway = Flyway.configure()
                .locations("db/migration/tenants")
                .dataSource(dataSource)
                .schemas(schema)
                .load();
        return flyway.migrate();
    }

}
