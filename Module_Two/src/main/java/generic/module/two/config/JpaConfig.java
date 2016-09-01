package generic.module.two.config;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Silviu on 9/1/16.
 */

/**
 * These are the standard configuration for jpa
 */
@Configuration
public class JpaConfig {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JpaConfig.class);

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.username"));
        dataSource.setPassword(environment.getProperty("jdbc.password"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        logger.warn("Loading jpa entity manager factory!");

        //hibernate vendor
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabase(Database.MYSQL);
        vendorAdapter.setGenerateDdl(false);

        //compose entity manager
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setDataSource(dataSource());

        //packages to scan
        entityManager.setPackagesToScan("com.corpo.komm.core.model");

        //set additional properties
        Properties properties = additionalProperties();
        entityManager.setJpaProperties(properties);

        return entityManager;
    }

    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        properties.setProperty("hibernate.id.new_generator_mappings", environment.getProperty("hibernate.id.new_generator_mappings"));
        properties.setProperty("hibernate.ejb.naming_strategy", environment.getProperty("hibernate.ejb.naming_strategy"));
        properties.setProperty("hbm2ddl.auto", environment.getProperty("hbm2ddl.auto"));

        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");

        return properties;
    }

}

