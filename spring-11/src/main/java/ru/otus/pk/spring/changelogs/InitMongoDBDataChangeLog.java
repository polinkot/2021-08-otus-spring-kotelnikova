package ru.otus.pk.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.bson.types.ObjectId;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Author author1 = new Author(ObjectId.get().toString(), "AuthorF1", "AuthorL1");
    private Author author2 = new Author(ObjectId.get().toString(), "AuthorF2", "AuthorL2");
    private Genre genre1 = new Genre(ObjectId.get().toString(), "Genre1");
    private Genre genre2 = new Genre(ObjectId.get().toString(), "Genre2");
    private Book book1;
    private Book book2;

    @ChangeSet(order = "000", id = "initBooks", author = "pk", runAlways = true)
    public void initBooks(BookRepository repository) {
        repository.save(new Book(null, "Book1", author1, genre1)).subscribe(b -> book1 = b);
        repository.save(new Book(null, "Book2", author2, genre2)).subscribe(b -> book2 = b);
        repository.save(new Book(null, "Book3", author1, genre1)).subscribe();
    }

    @ChangeSet(order = "001", id = "initComments", author = "pk", runAlways = true)
    public void initComments(CommentRepository repository) {
        repository.save(new Comment(null, "Comment1", book1)).subscribe();
        repository.save(new Comment(null, "Comment2", book2)).subscribe();
        repository.save(new Comment(null, "Comment3", book1)).subscribe();
    }
}
