package ru.otus.pk.spring.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.pk.spring.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryJpa implements CommentRepository {

    private final EntityManager em;

    @Override
    public Long count() {
        TypedQuery<Long> query = em.createQuery("select count(*) from Comment", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        Map<String, Object> properties = Map.of("javax.persistence.fetchgraph", em.getEntityGraph("Comment.Book"));
        return ofNullable(em.find(Comment.class, id, properties));
    }

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c", Comment.class);
        query.setHint("javax.persistence.fetchgraph", this.em.getEntityGraph("Comment.Book"));

        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public int deleteById(Long id) {
        Query query = em.createQuery("delete from Comment с where с.id = :id");
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    public List<Comment> findByBookId(Long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class);
        query.setParameter("bookId", bookId);
        query.setHint("javax.persistence.fetchgraph", this.em.getEntityGraph("Comment.Book"));

        return query.getResultList();
    }
}
