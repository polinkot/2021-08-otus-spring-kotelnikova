package ru.otus.pk.spring.resilience;

import ru.otus.pk.spring.domain.*;

import java.util.*;

public class Utils {
    public static final List<Class<? extends Throwable>> EXCEPTIONS = List.of(Exception.class);

    public static final Author AUTHOR = new Author(1L, "N/A", "N/A");
    public static final Genre GENRE = new Genre(1L, "N/A");
    public static final Book BOOK = new Book(1L, "N/A", AUTHOR, GENRE);

    public static void failureForDemo(String method) {
        if (new Random().nextBoolean()) {
            System.out.println(method + " success");
            return;
        }

        if (new Random().nextBoolean()) {
            System.out.println(method + " Exception for Demo");
            throw new IllegalStateException(method + " IllegalStateException for Demo");
        }

        try {
            System.out.println(method + " delayed for Demo");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<Book> booksFallback(Throwable e) {
        System.out.println(e.getClass() + "     " + e.getMessage());
        return new ArrayList<>();
    }

    public static Book bookFallback(Throwable e) {
        System.out.println(e.getMessage());
        return BOOK;
    }

    public static List<Author> authorsFallback(Throwable e) {
        System.out.println(e.getClass() + "     " + e.getMessage());
        return new ArrayList<>();
    }

    public static List<Genre> genresFallback(Throwable e) {
        System.out.println(e.getClass() + "     " + e.getMessage());
        return new ArrayList<>();
    }
}
