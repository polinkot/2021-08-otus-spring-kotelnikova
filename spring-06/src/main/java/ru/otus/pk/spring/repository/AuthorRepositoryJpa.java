package ru.otus.pk.spring.repository;

import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.model.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Repository
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    public AuthorRepositoryJpa(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return ofNullable(em.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        TypedQuery<Author> query = em.createQuery("select distinct a from Author a left join fetch a.books ", Author.class);
        return query.getResultList();
    }

    @Override
    public Author save(Author author) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

//    @Override
//    public OtusStudent save(OtusStudent student) {
//        if (student.getId() <= 0) {
//            em.persist(student);
//            return student;
//        } else {
//            return em.merge(student);
//        }
//    }
//
//    @Override
//    public Optional<OtusStudent> findById(long id) {
//        return Optional.ofNullable(em.find(OtusStudent.class, id));
//    }
//
//    @Override
//    public List<OtusStudent> findAll() {
//        EntityGraph<?> entityGraph = em.getEntityGraph("otus-student-avatars-entity-graph");
//        TypedQuery<OtusStudent> query = em.createQuery("select s from OtusStudent s join fetch s.emails", OtusStudent.class);
//        query.setHint("javax.persistence.fetchgraph", entityGraph);
//        return query.getResultList();
//    }
//
//    @Override
//    public List<OtusStudent> findByName(String name) {
//        TypedQuery<OtusStudent> query = em.createQuery("select s " +
//                        "from OtusStudent s " +
//                        "where s.name = :name",
//                OtusStudent.class);
//        query.setParameter("name", name);
//        return query.getResultList();
//    }
//
//    @Override
//    public void updateNameById(long id, String name) {
//        Query query = em.createQuery("update OtusStudent s " +
//                "set s.name = :name " +
//                "where s.id = :id");
//        query.setParameter("name", name);
//        query.setParameter("id", id);
//        query.executeUpdate();
//    }
//
//    @Override
//    public void deleteById(long id) {
//        Query query = em.createQuery("delete " +
//                        "from OtusStudent s " +
//                        "where s.id = :id");
//        query.setParameter("id", id);
//        query.executeUpdate();
//    }

}
