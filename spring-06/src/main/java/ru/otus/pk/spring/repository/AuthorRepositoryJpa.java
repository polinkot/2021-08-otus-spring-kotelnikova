package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.model.Author;
import ru.otus.pk.spring.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    private final EntityManager em;

    @Override
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(*) from Author", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a ", Author.class);
        return query.getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public int deleteById(Long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public List<Book> findBooks(Long id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.author.id = :id", Book.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", this.em.getEntityGraph("Book.plain"));

        return query.getResultList();
    }
}
