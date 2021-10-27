package ru.otus.pk.spring.repository;

import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.model.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class GenreRepositoryJpa implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    public GenreRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return ofNullable(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> findAll() {
        TypedQuery<Genre> query = em.createQuery("select distinct g from Genre g left join fetch g.books ", Genre.class);
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
}
