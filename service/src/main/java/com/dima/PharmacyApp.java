package com.dima;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class PharmacyApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PharmacyApp.class, args);
        log.info("Pharmacy application has started successfully...");
    }
}
