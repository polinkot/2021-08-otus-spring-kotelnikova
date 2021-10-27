package ru.otus.pk.spring.repository;

import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    public BookRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b ", Book.class);
        return query.getResultList();
    }

    @Override
    public Book save(Book Book) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
