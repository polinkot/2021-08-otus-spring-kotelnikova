package ru.otus.pk.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.bson.types.ObjectId;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private static final Author AUTHOR1 = new Author(ObjectId.get().toString(), "AuthorF1", "AuthorL1");
    private static final Author AUTHOR2 = new Author(ObjectId.get().toString(), "AuthorF2", "AuthorL2");
    private static final Genre GENRE1 = new Genre(ObjectId.get().toString(), "Genre1");
    private static final Genre GENRE2 = new Genre(ObjectId.get().toString(), "Genre2");
    private Book book1;
    private Book book2;

    @ChangeSet(order = "000", id = "initBooks", author = "pk", runAlways = true)
    public void initBooks(BookRepository repository) {
        repository.save(new Book(null, "Book1", AUTHOR1, GENRE1)).subscribe(b -> book1 = b);
        repository.save(new Book(null, "Book2", AUTHOR2, GENRE2)).subscribe(b -> book2 = b);
        repository.save(new Book(null, "Book3", AUTHOR1, GENRE1)).subscribe();
    }

    @ChangeSet(order = "001", id = "initComments", author = "pk", runAlways = true)
    public void initComments(CommentRepository repository) {
        repository.save(new Comment(null, "Comment1", book1)).subscribe();
        repository.save(new Comment(null, "Comment2", book2)).subscribe();
        repository.save(new Comment(null, "Comment3", book1)).subscribe();
    }
}
