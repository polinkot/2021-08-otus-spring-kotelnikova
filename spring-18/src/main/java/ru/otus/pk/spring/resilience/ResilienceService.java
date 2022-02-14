package ru.otus.pk.spring.resilience;

import org.springframework.stereotype.Service;
import ru.otus.pk.spring.domain.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResilienceService {
    public static final List<Class<? extends Throwable>> EXCEPTIONS = List.of(Exception.class);

    public static final Author AUTHOR = new Author(1L, "N/A", "N/A");
    public static final Genre GENRE = new Genre(1L, "N/A");
    public static final Book BOOK = new Book(1L, "N/A", AUTHOR, GENRE);

    public List<Book> booksFallback(Throwable e) {
        System.out.println(e.getClass() + "     " + e.getMessage());
        return new ArrayList<>();
    }

    public Book bookFallback(Throwable e) {
        System.out.println(e.getMessage());
        return BOOK;
    }

    public List<Author> authorsFallback(Throwable e) {
        System.out.println(e.getClass() + "     " + e.getMessage());
        return new ArrayList<>();
    }

    public List<Genre> genresFallback(Throwable e) {
        System.out.println(e.getClass() + "     " + e.getMessage());
        return new ArrayList<>();
    }

    public List<Comment> commentsFallback(Throwable e) {
        System.out.println(e.getClass() + "     " + e.getMessage());
        return new ArrayList<>();
    }
}
