package com.dima;

import com.dima.config.ApplicationConfiguration;
import lombok.Cleanup;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PharmacyApp {

    public static void main(String[] args) {
        @Cleanup var context = new AnnotationConfigApplicationContext();
        context.register(ApplicationConfiguration.class);
        context.refresh();
        System.out.println("");

    }
}
