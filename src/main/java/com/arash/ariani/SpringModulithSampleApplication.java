package com.arash.ariani;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class SpringModulithSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringModulithSampleApplication.class, args);
    }

    @EventListener
    void onApplicationStarted(ApplicationStartedEvent event) {
        System.out.println("Application started: " + event);
    }
} 