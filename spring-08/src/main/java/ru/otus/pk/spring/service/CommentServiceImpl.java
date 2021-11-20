package ru.otus.pk.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.dto.CommentDto;
import ru.otus.pk.spring.exception.LibraryException;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toSet;
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
    public List<CommentDto> getAll() {
        return repository.getAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Comment findById(String id) {
        return repository.findById(id).orElseThrow(() -> new LibraryException(format(COMMENT_NOT_FOUND, id)));
    }

    @Transactional(readOnly = true)
    @Override
    public CommentDto getById(String id) {
        return repository.getById(id).orElseThrow(() -> new LibraryException(format(COMMENT_NOT_FOUND, id)));
    }

    @Transactional
    @Override
    public CommentDto add(String text, String bookId) {
        Comment comment = new Comment(text);
        validate(comment);
        Comment savedComment = repository.save(comment);

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, bookId)));

        book.getComments().add(savedComment);
        bookRepository.save(book);

        return getById(savedComment.getId());
    }

    @Transactional
    @Override
    public CommentDto edit(String id, String text) {
        Comment comment = findById(id);
        comment.setText(text);
        validate(comment);

        Book book = bookRepository.findFirstByCommentsId(id);
        book.getComments().stream().filter(c -> c.getId().equals(id)).forEach(b -> b.setText(text));

        repository.save(comment);
        bookRepository.save(book);

        return getById(id);
    }

    @Transactional
    @Override
    public void deleteById(String id) {
        repository.deleteById(id);

        Book book = bookRepository.findFirstByCommentsId(id);
        book.getComments().removeIf(c -> c.getId().equals(id));
        bookRepository.save(book);
    }

    @Override
    public void deleteAll(List<Comment> comments) {
        repository.deleteAll(comments);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CommentDto> findByBookId(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new LibraryException(format(BOOK_NOT_FOUND, bookId)));
        return repository.getComments(book.getComments().stream().map(Comment::getId).collect(toSet()));
    }

    private void validate(Comment comment) {
        if (isEmpty(comment.getText())) {
            throw new LibraryException("Comment text is null or empty!");
        }
    }
}
