package ru.otus.pk.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.domain.Genre;
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
    private Book book3;

    @ChangeSet(order = "000", id = "dropDB", author = "pk", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initBooks", author = "pk", runAlways = true)
    public void initBooks(BookRepository repository) {
        book1 = repository.save(new Book(null, "Book1", author1, genre1));
        book2 = repository.save(new Book(null, "Book2", author2, genre2));
        book3 = repository.save(new Book(null, "Book3", author1, genre1));
    }

    @ChangeSet(order = "002", id = "initComments", author = "pk", runAlways = true)
    public void initComments(CommentRepository repository) {
        repository.save(new Comment(null, "Comment1", book1));
        repository.save(new Comment(null, "Comment2", book2));
        repository.save(new Comment(null, "Comment3", book1));
    }
}
