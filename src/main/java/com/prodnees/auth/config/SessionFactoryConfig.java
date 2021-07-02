package com.prodnees.auth.config;

import com.prodnees.auth.util.TenantUtil;
import com.prodnees.core.domain.user.UserAttributes;
import org.hibernate.SessionFactory;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.DRIVER;
import static org.hibernate.cfg.AvailableSettings.PASS;
import static org.hibernate.cfg.AvailableSettings.URL;
import static org.hibernate.cfg.AvailableSettings.USER;

@Configuration
public class SessionFactoryConfig {

    private final Environment environment;

    public SessionFactoryConfig(Environment environment) {
        this.environment = environment;
    }

    public SessionFactory getCurrentSession(String schema) {
        return getCurrentSession(schema, List.of(UserAttributes.class));
    }

    public SessionFactory getCurrentSession(String schema, List<Class<?>> annotatedClasses) {
        org.hibernate.cfg.Configuration config = new org.hibernate.cfg.Configuration();

        config.setProperty(DRIVER, environment.getProperty(LocalSettings.Datasource.DRIVER));//"com.mysql.cj.jdbc.Driver"
        config.setProperty(URL, environment.getProperty(LocalSettings.Datasource.URL) + "/" + schema);
        config.setProperty(USER, environment.getProperty(LocalSettings.Datasource.USERNAME));
        config.setProperty(PASS, environment.getProperty(LocalSettings.Datasource.PASSWORD));
        config.setProperty(DIALECT, environment.getProperty(LocalSettings.JPA.DB_PLATFORM));//"org.hibernate.dialect.MySQL8Dialect");
        config.setPhysicalNamingStrategy(new SpringPhysicalNamingStrategy());
        annotatedClasses.forEach(config::addAnnotatedClass);
        return config.buildSessionFactory();

    }


    /**
     * {@link SessionFactoryConfig#getCurrentSession(String)} does not fail during the build time since it is not a bean,
     * <i>Bean requires anything that goes as a parameter to be a Bean too</i>
     * <i>schema is not a bean</i>
     * <p>This method will throw an error during build time (IF NON OF THE UNIT TESTS ARE CALLING THE REAL DATABASE) if the url is not correct</p>
     *
     * @return
     */
    @Bean
    public boolean databaseDriverIsConnected() {
        getCurrentSession(TenantUtil.MASTER_SCHEMA).openSession();
        return true;
    }
}