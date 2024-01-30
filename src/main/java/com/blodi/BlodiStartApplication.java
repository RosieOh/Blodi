package com.blodi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BlodiStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlodiStartApplication.class, args);
    }

}
