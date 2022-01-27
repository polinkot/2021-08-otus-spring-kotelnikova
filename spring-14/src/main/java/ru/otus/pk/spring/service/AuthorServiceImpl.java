package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.repository.AuthorRepository;

import java.util.*;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Transactional
    @Override
    public Map<String, Author> createAuthors(List list) {
        Map<String, Author> authors = new HashMap<>();
        new ArrayList<Book>(list)
                .forEach(book -> authors.put(book.getAuthor().getMongoId(), book.getAuthor()));

        repository.findByMongoIdIn(authors.keySet())
                .forEach(author -> authors.put(author.getMongoId(), author));

        Set<Author> newAuthors = authors.values().stream()
                .filter(author -> author.getId() == null).collect(toSet());
        repository.saveAll(newAuthors)
                .forEach(author -> authors.put(author.getMongoId(), author));

        return authors;
    }
}
