package ru.otus.pk.spring.config;

import org.slf4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import ru.otus.pk.spring.domain.*;
import ru.otus.pk.spring.mongodomain.*;
import ru.otus.pk.spring.service.*;

import javax.persistence.EntityManagerFactory;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class JobConfig {
    private final Logger logger = getLogger("Batch");

    private static final int CHUNK_SIZE = 3;

    public static final String IMPORT_BOOK_JOB_NAME = "importBookJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private BookService boookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Bean
    public Job importBookJob(Step transformBooksStep, Step transformCommentsStep) {
        return jobBuilderFactory.get(IMPORT_BOOK_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(transformBooksStep)
                .next(transformCommentsStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                        logger.info("*******************");

                        List<Book2> books = jdbcTemplate.query("select * from book join mongo_book on book_id = id ", new BeanPropertyRowMapper<>(Book2.class));
                        books.forEach(System.out::println);

                        List<Author> authors = jdbcTemplate.query("select * from author join mongo_author on author_id = id ", new BeanPropertyRowMapper<>(Author.class));
                        authors.forEach(System.out::println);

                        List<Genre> genres = jdbcTemplate.query("select * from genre join mongo_genre on genre_id = id ", new BeanPropertyRowMapper<>(Genre.class));
                        genres.forEach(System.out::println);

                        List<Comment2> comments = jdbcTemplate.query("select * from comment ", new BeanPropertyRowMapper<>(Comment2.class));
                        comments.forEach(System.out::println);
                    }
                })
                .build();
    }

    @Bean
    public Step transformBooksStep(ItemReader<MongoBook> bookReader, JpaItemWriter<Book> bookWriter,
                                   ItemProcessor<MongoBook, Book> bookProcessor) {
        return stepBuilderFactory.get("step1")
                .<MongoBook, Book>chunk(CHUNK_SIZE)
                .reader(bookReader)
                .processor(bookProcessor)
                .writer(bookWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Book: Начало чтения");
                    }

                    public void afterRead(@NonNull MongoBook o) {
                        logger.info("Book: Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Book: Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Book: Начало записи");

                        Map<String, Author> authors = authorService.createAuthors(list);
                        Map<String, Genre> genres = genreService.createGenres(list);
                        new ArrayList<Book>(list)
                                .forEach(book -> {
                                    book.setAuthor(authors.get(book.getAuthor().getMongoId()));
                                    book.setGenre(genres.get(book.getGenre().getMongoId()));
                                });
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Book: Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Book: Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Book: Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Book: Ошибка пачки");
                    }
                })
                .build();
    }

    @Bean
    public MongoItemReader<MongoBook> bookReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<MongoBook>()
                .name("bookReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(MongoBook.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<MongoBook, Book> bookProcessor(TransformationService service) {
        return service::transformBook;
    }

    @Bean
    public JpaItemWriter<Book> bookWriter() {
        JpaItemWriter<Book> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Step transformCommentsStep(ItemReader<MongoComment> commentReader, JpaItemWriter<Comment> commentWriter,
                                      ItemProcessor<MongoComment, Comment> commentProcessor) {
        return stepBuilderFactory.get("step2")
                .<MongoComment, Comment>chunk(CHUNK_SIZE)
                .reader(commentReader)
                .processor(commentProcessor)
                .writer(commentWriter)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Comment: Начало чтения");
                    }

                    public void afterRead(@NonNull MongoComment o) {
                        logger.info("Comment: Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Comment: Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Comment: Начало записи");

                        Map<String, Book> books = boookService.findBooks(list);
                        new ArrayList<Comment>(list)
                                .forEach(comment -> comment.setBook(books.get(comment.getBook().getMongoId())));
                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Comment: Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Comment: Ошибка записи");
                    }
                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Comment: Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Comment: Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Comment: Ошибка пачки");
                    }
                })
                .build();
    }

    @Bean
    public MongoItemReader<MongoComment> commentReader(MongoTemplate template) {
        return new MongoItemReaderBuilder<MongoComment>()
                .name("commentReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(MongoComment.class)
                .sorts(new HashMap<>())
                .build();
    }

    @Bean
    public ItemProcessor<MongoComment, Comment> commentProcessor(TransformationService service) {
        return service::transformComment;
    }

    @Bean
    public JpaItemWriter<Comment> commentWriter() {
        JpaItemWriter<Comment> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
