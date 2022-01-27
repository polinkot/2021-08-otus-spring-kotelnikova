package ru.otus.pk.spring.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.github.cloudyrock.mongock.driver.mongodb.springdata.v3.decorator.impl.MongockTemplate;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import ru.otus.pk.spring.mongodomain.*;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    @ChangeSet(order = "000", id = "dropDB", author = "pk", runAlways = true)
    public void dropDB(MongoDatabase database){
        database.drop();
    }

    @ChangeSet(order = "001", id = "init", author = "pk", runAlways = true)
    public void init(MongockTemplate template) {
        MongoAuthor author1 = new MongoAuthor(ObjectId.get().toString(), "AuthorF1", "AuthorL1");
        MongoAuthor author2 = new MongoAuthor(ObjectId.get().toString(), "AuthorF2", "AuthorL2");
        MongoAuthor author3 = new MongoAuthor(ObjectId.get().toString(), "AuthorF3", "AuthorL3");

        MongoGenre genre1 = new MongoGenre(ObjectId.get().toString(), "Genre1");
        MongoGenre genre2 = new MongoGenre(ObjectId.get().toString(), "Genre2");

        MongoBook book1 = template.save(new MongoBook(null, "Book1", author1, genre1));
        MongoBook book2 = template.save(new MongoBook(null, "Book2", author2, genre2));
        template.save(new MongoBook(null, "Book11", author1, genre1));
        template.save(new MongoBook(null, "Book3", author3, genre1));
        template.save(new MongoBook(null, "Book33", author3, genre1));
        template.save(new MongoBook(null, "Book22", author2, genre1));

        template.insertAll(List.of(new MongoComment(null, "Comment1", book1),
                new MongoComment(null, "Comment2", book2),
                new MongoComment(null, "Comment3", book1)));

//        System.out.println(template.findAll(Book.class));
    }
}
