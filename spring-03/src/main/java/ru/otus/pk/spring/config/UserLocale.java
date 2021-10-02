package ru.otus.pk.spring.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Locale;

@AllArgsConstructor
@Data
public class UserLocale {
    private Locale locale;
}