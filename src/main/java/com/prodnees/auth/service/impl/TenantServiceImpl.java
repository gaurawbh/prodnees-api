/*
 * Copyright (c) This is an intellectual property of Neesum Technology Pvt Ltd.
 * Unauthorized usage of this property is prohibited  and
 * anyone found doing so will be prosecuted by Gauri Baba.
 */

package com.prodnees.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.prodnees.auth.config.SessionFactoryConfig;
import com.prodnees.auth.domain.ApplicationRole;
import com.prodnees.auth.domain.User;
import com.prodnees.auth.service.TenantService;
import com.prodnees.core.domain.doc.DocSubType;
import com.prodnees.core.domain.doc.DocTypeEnum;
import com.prodnees.core.domain.doc.NeesDoctype;
import com.prodnees.core.domain.enums.ObjectRight;
import com.prodnees.core.domain.user.NeesObject;
import com.prodnees.core.domain.user.NeesObjectRight;
import com.prodnees.core.domain.user.UserAttributes;
import com.prodnees.core.util.LocalAssert;
import com.prodnees.shelf.domain.ProductGroup;
import com.prodnees.shelf.domain.ProductGroupEnum;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static com.prodnees.auth.util.TenantUtil.VALID_SCHEMA_PATTERN;


@Service
@Transactional
public class TenantServiceImpl implements TenantService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final List<Class<?>> annotatedClasses = ImmutableList.of(
            UserAttributes.class,
            NeesDoctype.class,
            NeesObjectRight.class,
            ProductGroup.class);

    private final DataSource dataSource;
    private final SessionFactoryConfig sessionFactoryConfig;

    public TenantServiceImpl(DataSource dataSource,
                             SessionFactoryConfig sessionFactoryConfig) {
        this.dataSource = dataSource;
        this.sessionFactoryConfig = sessionFactoryConfig;
    }

    @Override
    public void createNewSchema(User user) throws JsonProcessingException {
        String schema = user.getSchemaInstance();
        LocalAssert.isTrue(schema.matches(VALID_SCHEMA_PATTERN), "invalid schema name");
        MigrateResult re = initNewSchema(schema);
        if (re.migrationsExecuted > 0) {
            logger.info("Schema {} was successfully created", schema);
        }
        Session session = sessionFactoryConfig.getCurrentSession(schema, annotatedClasses).openSession();

        Transaction tx = session.beginTransaction();

        addUserAttributes(session, user);
        addDocTypes(session);
        addNeesObjectRights(session, user);
        addProductGroups(session);

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

    void addUserAttributes(Session session, User user) {
        UserAttributes userAttributes = new UserAttributes()
                .setUserId(user.getId())
                .setEmail(user.getEmail())
                .setRole(ApplicationRole.appOwner)
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName());
        session.save(userAttributes);
    }

    void addDocTypes(Session session) throws JsonProcessingException {
        DocTypeEnum[] docTypeEnums = DocTypeEnum.values();
        for (DocTypeEnum docType : docTypeEnums) {
            NeesDoctype neesDocType = new NeesDoctype();
            List<DocSubType> subTypes = docType.getDocSubTypeList();
            ObjectMapper objectMapper = new ObjectMapper();
            neesDocType.setName(docType.name())
                    .setDescription(String.format("Documents related to %s will use this doctype", docType.name()))
                    .setSys(true)
                    .setActive(true)
                    .setSubTypesJson(objectMapper.writeValueAsString(subTypes));
            session.save(neesDocType);
        }
    }

    void addNeesObjectRights(Session session, User user) {
        NeesObject[] neesObjects = NeesObject.values();
        for (NeesObject neesObject : neesObjects) {
            NeesObjectRight neesObjectRight = new NeesObjectRight();
            neesObjectRight.setUserId(user.getId())
                    .setNeesObject(neesObject)
                    .setObjectRight(ObjectRight.full);
            session.save(neesObjectRight);
        }
    }


    private void addProductGroups(Session session) {
        ProductGroupEnum[] productGroupEnums = ProductGroupEnum.values();
        for (ProductGroupEnum groupEnum : productGroupEnums) {
            ProductGroup productGroup = new ProductGroup()
                    .setPrivateKey(groupEnum.name())
                    .setLabel(groupEnum.getLabel())
                    .setDescription(groupEnum.getDescription())
                    .setActive(groupEnum.isActive())
                    .setSys(true);
            session.save(productGroup);
        }
    }

}
