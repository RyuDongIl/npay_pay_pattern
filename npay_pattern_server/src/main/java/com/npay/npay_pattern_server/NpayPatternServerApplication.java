package com.npay.npay_pattern_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NpayPatternServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NpayPatternServerApplication.class, args);
    }
}
