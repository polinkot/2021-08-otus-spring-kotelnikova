package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Repository
public class GenreRepositoryJpa implements GenreRepository {

    private final EntityManager em;

    @Override
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(*) from Genre", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g ", Genre.class);
        return query.getResultList();
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public int deleteById(Long id) {
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public List<Book> findBooks(Long id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b where b.genre.id = :id", Book.class);
        query.setParameter("id", id);
        query.setHint("javax.persistence.fetchgraph", this.em.getEntityGraph("Book.plain"));

        return query.getResultList();
    }
}
