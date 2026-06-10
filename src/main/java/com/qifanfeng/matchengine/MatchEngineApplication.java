package com.qifanfeng.matchengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MatchEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchEngineApplication.class, args);
    }
}
