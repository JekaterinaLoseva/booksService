package com.company.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    protected Application() {
    }

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

