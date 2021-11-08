package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Repository
public class BookRepositoryJpa implements BookRepository {

    private final EntityManager em;

    @Override
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(*) from Book", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Book> findById(Long id) {
        Map<String, Object> properties = Map.of("javax.persistence.fetchgraph", em.getEntityGraph("Book.Author.Genre"));
        return ofNullable(em.find(Book.class, id, properties));
    }

    @Override
    public List<Book> findAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b " +
                "join fetch b.author " +
                "join fetch b.genre ", Book.class);
        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public int deleteById(Long id) {
        Query query = em.createQuery("delete from Book b where b.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
