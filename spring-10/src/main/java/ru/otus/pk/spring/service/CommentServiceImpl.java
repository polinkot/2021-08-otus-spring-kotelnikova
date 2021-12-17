package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.exception.ObjectNotFoundException;
import ru.otus.pk.spring.repository.CommentRepository;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    public static final String COMMENT_NOT_FOUND = "Comment not found!!! id = %s";

    private final CommentRepository repository;

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return repository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(format(COMMENT_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Comment save(Comment comment) {
        validate(comment);
        return repository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ObjectNotFoundException(format(COMMENT_NOT_FOUND, id), e);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(Long bookId) {
        return repository.findByBookId(bookId);
    }

    private void validate(Comment comment) {
        if (isEmpty(comment.getText())) {
            throw new LibraryException("Comment text is null or empty!");
        }
    }
}
