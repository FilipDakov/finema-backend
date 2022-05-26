package com.fd.finema;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FinemaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinemaApplication.class, args);
    }

}
