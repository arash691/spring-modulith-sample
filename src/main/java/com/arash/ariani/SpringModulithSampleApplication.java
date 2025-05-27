package com.arash.ariani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.modulith.ApplicationModuleListener;
import org.springframework.modulith.events.ApplicationModuleEvent;

@SpringBootApplication
public class SpringModulithSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringModulithSampleApplication.class, args);
    }

    @ApplicationModuleListener
    void on(ApplicationModuleEvent event) {
        System.out.println("Module event: " + event);
    }
} 