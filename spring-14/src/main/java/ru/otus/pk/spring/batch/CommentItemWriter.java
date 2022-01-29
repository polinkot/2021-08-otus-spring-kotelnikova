package ru.otus.pk.spring.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.*;

@RequiredArgsConstructor
public class CommentItemWriter<T> extends JpaItemWriter<T> {

    private final BookRepository bookRepository;

    @Override
    public void write(List list) {
        List<Comment> comments = new ArrayList<Comment>(list);
        Map<String, Book> books = findBooks(comments);
        comments.forEach(comment -> comment.setBook(books.get(comment.getBook().getMongoId())));

        super.write(list);
    }

    @Transactional(readOnly = true)
    public Map<String, Book> findBooks(List<Comment> comments) {
        Map<String, Book> books = new HashMap<>();
        comments.forEach(comment -> books.put(comment.getBook().getMongoId(), comment.getBook()));

        bookRepository.findByMongoIdIn(books.keySet())
                .forEach(book -> books.put(book.getMongoId(), book));

        return books;
    }
}
