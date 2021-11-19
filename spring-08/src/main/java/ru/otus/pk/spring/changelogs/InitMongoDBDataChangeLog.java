package ru.otus.pk.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Comment;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.repository.AuthorRepository;
import ru.otus.pk.spring.repository.BookRepository;
import ru.otus.pk.spring.repository.CommentRepository;
import ru.otus.pk.spring.repository.GenreRepository;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private Comment comment1;
    private Comment comment2;
    private Comment comment3;
    private Book book1;
    private Book book2;
    private Book book3;

    @ChangeSet(order = "000", id = "dropDB", author = "pk", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initComments", author = "pk", runAlways = true)
    public void initComments(CommentRepository repository) {
        comment1 = repository.save(new Comment("Comment1"));
        comment2 = repository.save(new Comment("Comment2"));
        comment3 = repository.save(new Comment("Comment3"));
    }

    @ChangeSet(order = "002", id = "initBooks", author = "pk", runAlways = true)
    public void initBooks(BookRepository repository) {
        book1 = repository.save(new Book("Book1", comment1, comment3));
        book2 = repository.save(new Book("Book2", comment2));
        book3 = repository.save(new Book("Book3"));
    }

    @ChangeSet(order = "003", id = "initAuthors", author = "pk", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        repository.save(new Author("AuthorF1", "AuthorL1", book1));
        repository.save(new Author("AuthorF2", "AuthorL2", book2, book3));
        repository.save(new Author("AuthorF3", "AuthorL3"));
        repository.save(new Author("AuthorF4", "AuthorL4"));
    }

    @ChangeSet(order = "004", id = "initGenres", author = "pk", runAlways = true)
    public void initGenres(GenreRepository repository) {
        repository.save(new Genre("Genre1", book1));
        repository.save(new Genre("Genre2", book2, book3));
    }
}
