package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static ru.otus.pk.spring.service.BookServiceImpl.BOOK_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    public static final String COMMENT_NOT_FOUND = "Comment not found!!! id = %s";

    private final CommentRepository repository;

    private final BookRepository bookRepository;

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
    public Comment findById(String id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(COMMENT_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public Comment add(String text, String bookId) {
        Comment comment = new Comment();
        comment.setText(text);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, bookId)));
        comment.setBook(book);

        validate(comment);
        return repository.save(comment);
    }

    @Transactional
    @Override
    public Comment edit(String id, String text) {
        Comment comment = findById(id);
        comment.setText(text);
        validate(comment);

        return repository.save(comment);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByBookId(String bookId) {
        return repository.findByBookId(bookId);
    }

    @Transactional
    @Override
    public void deleteByBookId(String bookId) {
        repository.deleteByBookId(bookId);
    }

    private void validate(Comment comment) {
        List<String> errors = new ArrayList<>();

        if (isEmpty(comment.getText())) {
            errors.add("Comment text is null or empty!");
        }

        if (isEmpty(comment.getBook())) {
            errors.add("Comment book is null!");
        }

        if (!isEmpty(errors)) {
            throw new LibraryException(join("\n", errors));
        }
    }
}
