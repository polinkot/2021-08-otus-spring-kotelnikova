package ru.otus.pk.spring.repository;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.pk.spring.model.Author;
import ru.otus.pk.spring.model.Book;
import ru.otus.pk.spring.model.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.util.ObjectUtils.isEmpty;

@DisplayName("Репозиторий для работы с книгами должен ")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    private static final Long EXISTING_BOOK_ID = 1L;
    public static final Long DELETABLE_BOOK_ID = 2L;

    public static final Long EXISTING_AUTHOR_ID = 1L;
    public static final Long EXISTING_GENRE_ID = 1L;
    public static final Long EXISTING_AUTHOR_ID_2 = 2L;
    public static final Long EXISTING_GENRE_ID_2 = 2L;

    private static final int EXPECTED_NUMBER_OF_BOOKS = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;

    @Autowired
    private BookRepositoryJpa repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("загружать книгу по id")
    @Test
    void shouldFindExpectedBookById() {
        val actualBook = repository.findById(EXISTING_BOOK_ID);
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("загружать список всех книг")
    @Test
    void findAll() {
        Statistics statistics = new Statistics(em);
        statistics.setStatisticsEnabled(true);

        val books = repository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> !isEmpty(b.getName()));
        assertThat(statistics.getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("добавлять книгу в БД")
    @Test
    void shouldInsertBook() {
        Author existingAuthor = em.find(Author.class, EXISTING_AUTHOR_ID);
        Genre existingGenre = em.find(Genre.class, EXISTING_GENRE_ID);

        String name = "newName";
        Book savedBook = repository.save(new Book(null, name, existingAuthor, existingGenre));

        Book actualBook = em.find(Book.class, savedBook.getId());
        assertThat(actualBook).isNotNull();
        assertThat(actualBook).extracting("name", "author", "genre")
                .doesNotContainNull()
                .containsExactly(name, existingAuthor, existingGenre);
    }

    @DisplayName("редактировать книгу в БД")
    @Test
    void shouldUpdateBook() {
        Author existingAuthor2 = em.find(Author.class, EXISTING_AUTHOR_ID_2);
        Genre existingGenre2 = em.find(Genre.class, EXISTING_GENRE_ID_2);
        Book existingBook = em.find(Book.class, EXISTING_BOOK_ID);
        existingBook.setName("updatedName");
        existingBook.setAuthor(existingAuthor2);
        existingBook.setGenre(existingGenre2);
        Book savedBook = repository.save(existingBook);

        Book actualBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(savedBook);
    }

    @DisplayName("удалять книгу по id")
    @Test
    void shouldCorrectyDeleteBookById() {
        Book book = em.find(Book.class, DELETABLE_BOOK_ID);
        assertThat(book).isNotNull();

        repository.deleteById(DELETABLE_BOOK_ID);
        em.detach(book);

        book = em.find(Book.class, DELETABLE_BOOK_ID);
        assertThat(book).isNull();
    }
}