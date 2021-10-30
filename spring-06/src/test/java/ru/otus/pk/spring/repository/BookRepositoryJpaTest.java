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
import ru.otus.pk.spring.model.Comment;
import ru.otus.pk.spring.model.Genre;

import java.util.ArrayList;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
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
    private static final int EXPECTED_QUERIES_COUNT = 2;

    @Autowired
    private BookRepositoryJpa repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName(" должен загружать информацию о нужной книге по id")
    @Test
    void shouldFindExpectedBookById() {
        val actualBook = repository.findById(EXISTING_BOOK_ID);
        val expectedBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void findAll() {
        Statistics statistics = new Statistics(em);
        statistics.setStatisticsEnabled(true);

        System.out.println("\n\n\n\n----------------------------------------------------------------------------------------------------------");
        val books = repository.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> b.getAuthor() != null)
                .allMatch(b -> b.getGenre() != null)
                .allMatch(b -> !b.getName().equals(""));
        System.out.println("----------------------------------------------------------------------------------------------------------\n\n\n\n");
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
        assertThat(actualBook).extracting("name", "author.id", "genre.id")
                .doesNotContainNull()
                .containsExactly(name, existingAuthor.getId(), existingGenre.getId());
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

    @DisplayName("добавить комментарий к книге")
    @Test
    void shouldAddCommentToBook() {
        String newComment = "newComment";
        Book existingBook = em.find(Book.class, EXISTING_BOOK_ID);
        existingBook.getComments().add(new Comment(null, newComment));
        repository.save(existingBook);

        Book actualBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(actualBook.getComments().stream().map(Comment::getText).collect(toList())).contains(newComment);
    }

    @DisplayName("удалить комментарий к книге")
    @Test
    void shouldRemoveCommentFromBook() {
        Book existingBook = em.find(Book.class, EXISTING_BOOK_ID);
        Comment comment = new ArrayList<>(existingBook.getComments()).get(0);
        existingBook.getComments().remove(comment);
        repository.save(existingBook);
        em.detach(comment);

        Book actualBook = em.find(Book.class, EXISTING_BOOK_ID);
        assertThat(actualBook.getComments()).doesNotContain(comment);
    }

    @DisplayName("удалять заданную книгу по id")
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