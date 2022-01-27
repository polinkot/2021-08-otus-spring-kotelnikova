package ru.otus.pk.spring.config;

import org.slf4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.mongodomain.MongoBook;
import ru.otus.pk.spring.repository.AuthorRepository;
import ru.otus.pk.spring.service.BookTransformationService;
import ru.otus.pk.spring.service.CleanUpService;

import javax.persistence.EntityManagerFactory;
import java.util.*;

import static java.util.stream.Collectors.toSet;
import static org.slf4j.LoggerFactory.getLogger;

@Configuration
public class JobConfig {
    private final Logger logger = getLogger("Batch");

    private static final int CHUNK_SIZE = 3;

    public static final String IMPORT_USER_JOB_NAME = "importUserJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CleanUpService cleanUpService;


    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Job importUserJob(Step transformBooksStep, Step cleanUpStep) {
        return jobBuilderFactory.get(IMPORT_USER_JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(transformBooksStep)
                .next(cleanUpStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец job");
                    }
                })
                .build();
    }

    @Bean
    public Step transformBooksStep(ItemReader<MongoBook> reader, JpaItemWriter<Book> writer,
                                   ItemProcessor<MongoBook, Book> itemProcessor) {
        return stepBuilderFactory.get("step1")
                .<MongoBook, Book>chunk(CHUNK_SIZE)
                .reader(reader)
                .processor(itemProcessor)
                .writer(writer)
                .listener(new ItemReadListener<>() {
                    public void beforeRead() {
                        logger.info("Начало чтения");
                    }

                    public void afterRead(@NonNull MongoBook o) {
                        logger.info("Конец чтения");
                    }

                    public void onReadError(@NonNull Exception e) {
                        logger.info("Ошибка чтения");
                    }
                })
                .listener(new ItemWriteListener<>() {
                    public void beforeWrite(@NonNull List list) {
                        logger.info("Начало записи");

                        Map<String, Author> authors = new HashMap<>();
                        new ArrayList<Book>(list).forEach(book ->
                                authors.put(book.getAuthor().getMongoId(), book.getAuthor())
                        );

                        authorRepository.findByMongoIds(authors.keySet())
                                .forEach(author -> authors.put(author.getMongoId(), author));

//                        Map<String, Author> existingAuthors = ofEntries(authorRepository.findByMongoIds(authors.keySet())
//                                .toArray(Entry[]::new));
//                        authors.putAll(existingAuthors);

                        Set<Author> newAuthors = authors.values().stream()
                                .filter(author -> author.getId() == null).collect(toSet());
                        authorRepository.saveAll(newAuthors)
                                .forEach(author -> authors.put(author.getMongoId(), author));

                        new ArrayList<Book>(list).forEach(book -> book.setAuthor(authors.get(book.getAuthor().getMongoId())));


//                        Author saved = authorRepository
//                                .save(new Author(null, "book.getAuthor().getFirstName()", "book.getAuthor().getLastName()", "97987987" + System.currentTimeMillis()));
//                        System.out.println("__________" + saved);
//
//                        list.forEach(book -> ((Book)book).setAuthor(saved));

//                        System.out.println("##############################");
//                        System.out.println(list.get(0).getClass());
//                        ((ru.otus.pk.spring.jpadomain.Book)list.get(0))
//                                .setAuthor(((ru.otus.pk.spring.jpadomain.Book) list.get(1)).getAuthor());

//                        ((Book) list.get(0)).setAuthor(new Author(null, "wwww", "123123"));
//                        Author author = ofNullable(entityManager.find(Author.class, 1L))
//                                .orElse(null);
////                                .orElse(new Author(null, book.getAuthor().getFirstName(), book.getAuthor().getLastName()));
//                        System.out.println("***********************author");
//                        System.out.println(author);
//
//                        Genre genre = ofNullable(entityManager.find(Genre.class,1L))
//                                .orElse(null);
////                                .orElse(new Genre(null, book.getGenre().getName()));
                        System.out.println();
//                        System.out.println(genre);


                    }

                    public void afterWrite(@NonNull List list) {
                        logger.info("Конец записи");
                    }

                    public void onWriteError(@NonNull Exception e, @NonNull List list) {
                        logger.info("Ошибка записи");
                    }
                })
//                .listener(new ItemProcessListener<>() {
//                    public void beforeProcess(Book o) {
//                        logger.info("Начало обработки");
//                    }
//
//                    public void afterProcess(@NonNull Book o, ru.otus.pk.spring.jpadomain.Book o2) {
//                        logger.info("Конец обработки");
//                    }
//
//                    public void onProcessError(@NonNull Book o, @NonNull Exception e) {
//                        logger.info("Ошибка обработки");
//                    }
//                })
                .listener(new ChunkListener() {
                    public void beforeChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Начало пачки");
                    }

                    public void afterChunk(@NonNull ChunkContext chunkContext) {
                        logger.info("Конец пачки");
                    }

                    public void afterChunkError(@NonNull ChunkContext chunkContext) {
                        logger.info("Ошибка пачки");
                    }
                })
//                .taskExecutor(new SimpleAsyncTaskExecutor())
                .build();
    }

    //    @StepScope
    @Bean
    public MongoItemReader<MongoBook> reader(MongoTemplate template) {
        return new MongoItemReaderBuilder<MongoBook>()
                .name("bookItemReader")
                .template(template)
                .jsonQuery("{}")
                .targetType(MongoBook.class)
                .sorts(new HashMap<>())
                .build();
    }

    //    @StepScope
    @Bean
    public ItemProcessor<MongoBook, Book> processor(BookTransformationService service) {
        return service::transform;
    }

    @Bean
    @StepScope
    public JpaItemWriter<Book> writer() {
        JpaItemWriter<Book> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public MethodInvokingTaskletAdapter cleanUpTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(cleanUpService);
        adapter.setTargetMethod("cleanUp");

        return adapter;
    }

    @Bean
    public Step cleanUpStep() {
        return this.stepBuilderFactory.get("cleanUpStep")
                .tasklet(cleanUpTasklet())
                .build();
    }
}
