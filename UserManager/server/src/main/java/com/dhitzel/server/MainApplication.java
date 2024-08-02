package com.dhitzel.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Need to exclude DataSourceAutoConfiguration.class
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class MainApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {	
        SpringApplication.run(MainApplication.class, args); 
    }
}