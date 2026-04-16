package com.example.softlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
// @EntityScan(basePackages = "com.example.softlearning.applicationcore.entity")
public class SoftlearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(SoftlearningApplication.class, args);
    }
}
