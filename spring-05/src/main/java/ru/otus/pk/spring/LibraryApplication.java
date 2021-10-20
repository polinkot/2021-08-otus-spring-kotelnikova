package ru.otus.pk.spring;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.h2.tools.Console;

@SpringBootApplication
public class LibraryApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
        Console.main(args);
    }
}