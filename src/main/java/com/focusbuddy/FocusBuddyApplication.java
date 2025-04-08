package com.focusbuddy;

import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FocusBuddyApplication {
    private static final Logger logger = LoggerFactory.getLogger(FocusBuddyApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(FocusBuddyApplication.class, args);
            logger.info("<<<<<<<<<<<< FocusBuddyApplication started successfully >>>>>>>>>>>>");
            if (args.length > 0) {
                logger.info("Application started with arguments:");
                for (String arg : args) {
                    logger.info("ARG: {}", arg);
                }
            }
        } catch (Exception e) {
            logger.error("Application failed to start: {}", e.getMessage(), e);
        }
    }

    @PreDestroy
    public void onShutdown() {
        logger.info("<<<<<<<<<<<< FocusBuddyApplication is shutting down >>>>>>>>>>>>");
    }
}
