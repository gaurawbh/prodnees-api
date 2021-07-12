package com.prodnees.auth.config;

import com.prodnees.auth.dao.CompanyDao;
import com.prodnees.auth.util.TenantUtil;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .locations("db/migration/master")
                .dataSource(dataSource)
                .schemas(TenantUtil.MASTER_SCHEMA)
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .load();
        flyway.migrate();
        return flyway;
    }


    @Bean
    CommandLineRunner commandLineRunner(CompanyDao companyDao, DataSource dataSource) {
        return args -> {
            companyDao.findAll().stream()
                    .filter(company -> !company.getSchemaInstance().equals(TenantUtil.MASTER_SCHEMA))
                    .forEach(company -> {
                        logger.info(String.format("--- Flyway Migration Starting for %s ---", company.getSchemaInstance()));
                        Flyway flyway = Flyway.configure()
                                .locations("db/migration/tenants")
                                .dataSource(dataSource)
                                .schemas(company.getSchemaInstance())
                                .load();
                        flyway.migrate();
                        logger.info(String.format("--- Flyway Migration Complete for %s ---", company.getSchemaInstance()));
                    });
            logger.info("--- Flyway Migration Checks complete ---");
        };
    }
}
