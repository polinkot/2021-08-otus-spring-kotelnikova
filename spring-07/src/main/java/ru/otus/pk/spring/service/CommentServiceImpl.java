package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.CommentRepository;

import java.util.List;

import static java.lang.String.format;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    public static final String COMMENT_NOT_FOUND = "Comment not found!!! id = %s";

    private final CommentRepository repository;

    private final BookService bookService;

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
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(COMMENT_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Comment save(Long id, String text, Long bookId) {
        Comment comment = id != null ? findById(id) : new Comment(null, text, bookService.findById(bookId));
        comment.setText(text);

        validate(comment);
        return repository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
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
