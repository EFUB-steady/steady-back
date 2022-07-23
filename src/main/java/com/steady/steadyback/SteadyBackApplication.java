package com.steady.steadyback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SteadyBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(SteadyBackApplication.class, args);
    }

}
