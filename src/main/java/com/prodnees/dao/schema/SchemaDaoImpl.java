package com.prodnees.dao.schema;

import com.prodnees.config.constraints.SchemaName;
import com.prodnees.dao.BaseDao;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;

@Repository
public class SchemaDaoImpl extends BaseDao implements SchemaDao {
    private static final String DATA_RESOURCE_CLASSPATH = "classpath:data/%s";

    private final ResourceLoader resourceLoader;

    public SchemaDaoImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void createSchemaTest(@SchemaName String schema) throws SQLException {
//        getJdbcTemplate().execute(String.format("create schema %s", schema));

        Resource resource1 = resourceLoader.getResource(String.format(DATA_RESOURCE_CLASSPATH, "newSchema.sql"));
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource1);

        DataSource dataSource = getDataSource();
        databasePopulator.execute(dataSource);
    }
}
