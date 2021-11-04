package ru.otus.pk.spring.shell;

import ru.otus.pk.spring.exception.LibraryException;

import static java.util.Optional.ofNullable;

public class CheckUtils {
    public static void checkId(Long id, String message) {
        ofNullable(id).orElseThrow(() -> new LibraryException(message));
    }
}
