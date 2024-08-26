package com.dhitzel.server.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class JpaConfig {

    private Logger logger = LoggerFactory.getLogger(JpaConfig.class);

    @Value("${jpa.datasource.url}")
    private String datasourceUrl;
    @Value("${jpa.datasource.username}")
    private String datasourceUsername;
    @Value("${jpa.datasource.password}")
    private String datasourcePassword;

    @Bean(name="jpaDataSource")
    public DataSource jpaDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(datasourceUrl); 
        dataSource.setUsername(datasourceUsername);
        dataSource.setPassword(datasourcePassword);
        
        return dataSource;
    }

    @Bean
    public HibernateJpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        try {
            LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
            localContainerEntityManagerFactoryBean.setDataSource(jpaDataSource());
            localContainerEntityManagerFactoryBean.setPackagesToScan("com.dhitzel.server.entity");
            localContainerEntityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
            return localContainerEntityManagerFactoryBean;
        } catch (Exception e) {
            logger.error("JpaConfigurationImpl.entityManagerFactory(): " + e.getMessage());
        }

        return new LocalContainerEntityManagerFactoryBean();
     }
}
