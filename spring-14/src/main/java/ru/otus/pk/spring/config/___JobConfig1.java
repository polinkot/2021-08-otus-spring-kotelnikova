package ru.otus.pk.spring.config;

import org.slf4j.Logger;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.builder.MongoItemReaderBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import ru.otus.pk.spring.mongodomain.MongoBook;
import ru.otus.pk.spring.mongodomain.Book2;
import ru.otus.pk.spring.service.CleanUpService;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

//@Configuration
public class ___JobConfig1 {
    private final Logger logger = getLogger("Batch");

    private static final int CHUNK_SIZE = 5;

    public static final String IMPORT_USER_JOB_NAME = "importUserJob";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private CleanUpService cleanUpService;


    @Autowired
    JdbcTemplate jdbcTemplate;


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
    public ItemProcessor<MongoBook, Book2> processor(/*HappyBirthdayService happyBirthdayService*/) {
//        return happyBirthdayService::doHappyBirthday;
        return book -> new Book2(null, book.getName(), 2L, 2L);
    }

//    @StepScope
//    @Bean
//    public FlatFileItemWriter<Book> writer() {
//        return new FlatFileItemWriterBuilder<Book>()
//                .name("personItemWriter")
//                .resource(new FileSystemResource("output.dat"))
//                .lineAggregator(new DelimitedLineAggregator<>())
//                .build();
//    }

    @Bean
    public JdbcBatchItemWriter<Book2> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Book2>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO book (brand, origin, characteristics) VALUES (:brand, :origin, :characteristics)")
                .sql("insert into book (`name`, author_id, genre_id) values (:name, :author_id, :genre_id)")
                .dataSource(dataSource)
                .build();
    }

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
    public Step transformBooksStep(ItemReader<MongoBook> reader, JdbcBatchItemWriter<Book2> writer,
                                   ItemProcessor<MongoBook, Book2> itemProcessor) {
        return stepBuilderFactory.get("step1")
                .<MongoBook, Book2>chunk(CHUNK_SIZE)
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
                    }

                    public void afterWrite(@NonNull List list) {

                        List<Book2> books = jdbcTemplate.query("select * from book ", new BeanPropertyRowMapper<>(Book2.class));
                        System.out.println("**********books****************");
                        System.out.println(books);

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
//                    public void afterProcess(@NonNull Book o, Book o2) {
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

    @Bean
    public MethodInvokingTaskletAdapter cleanUpTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(cleanUpService);
        adapter.setTargetMethod("cleanUp");

        return adapter;
    }

    @Bean
    public Step cleanUpStep() {
//        List<Book2> books = jdbcTemplate.query("select * from book ", new BeanPropertyRowMapper<>());
//        System.out.println("**********books****************");
//        System.out.println(books);


        return this.stepBuilderFactory.get("cleanUpStep")
                .tasklet(cleanUpTasklet())
                .build();
    }
}
