package ru.otus.pk.spring.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.pk.spring.domain.Author;
import ru.otus.pk.spring.domain.Book;
import ru.otus.pk.spring.domain.Genre;
import ru.otus.pk.spring.dto.AuthorDto;
import ru.otus.pk.spring.dto.BookDto;
import ru.otus.pk.spring.dto.GenreDto;
import ru.otus.pk.spring.repository.BookRepository;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Сервис для работы с книгами должен ")
@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {

    private static final AuthorDto AUTHOR_DTO = new AuthorDto("307f191e810c19729de860ea", "AuthorF", "AuthorL");
    private static final GenreDto GENRE_DTO = new GenreDto("407f191e810c19729de860ea", "Genre1");
    private static final BookDto BOOK_DTO = new BookDto("507f191e810c19729de860ea", "Book1", AUTHOR_DTO, GENRE_DTO);

    private static final Author AUTHOR = new Author(AUTHOR_DTO.getId(), AUTHOR_DTO.getFirstName(), AUTHOR_DTO.getLastName(), new HashSet<>());
    private static final Genre GENRE = new Genre(GENRE_DTO.getId(), GENRE_DTO.getName(), new HashSet<>());
    private static final Book BOOK = dtoToBook(BOOK_DTO);

    @MockBean
    private BookRepository repository;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;
    @MockBean
    private CommentService commentService;

    @Autowired
    private BookServiceImpl service;

    @DisplayName("возвращать ожидаемое количество книг")
    @Test
    void shouldReturnExpectedBookCount() {
        long expectedCount = 2;

        given(repository.count()).willReturn(expectedCount);

        long actualCount = service.count();
        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @DisplayName("возвращать ожидаемый список книг")
    @Test
    void shouldReturnExpectedBooksList() {
        given(repository.getAll()).willReturn(List.of(BOOK_DTO));

        List<BookDto> actualList = service.getAll();
        Assertions.assertThat(actualList).usingFieldByFieldElementComparator().containsExactly(BOOK_DTO);
    }

    @DisplayName("возвращать ожидаемую книгу по id")
    @Test
    void shouldReturnExpectedBookById() {
        given(repository.getById(BOOK_DTO.getId())).willReturn(Optional.of(BOOK_DTO));

        BookDto actualBook = service.getById(BOOK_DTO.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(BOOK_DTO);
    }

    @DisplayName("добавлять книгу")
    @Test
    void shouldInsertBook() {
        given(repository.save(any(Book.class))).willReturn(BOOK);
        given(authorService.findById(AUTHOR.getId())).willReturn(AUTHOR);
        given(genreService.findById(GENRE.getId())).willReturn(GENRE);
        given(repository.getById(BOOK_DTO.getId())).willReturn(Optional.of(BOOK_DTO));

        BookDto actualBook = service.add(BOOK_DTO.getName(),
                AUTHOR_DTO.getId(), null, null,
                GENRE_DTO.getId(), null);
        assertThat(actualBook).isEqualTo(BOOK_DTO);
    }

    @DisplayName("редактировать книгу")
    @Test
    void shouldUpdateBook() {
        given(repository.findById(BOOK.getId())).willReturn(Optional.of(BOOK));
        given(repository.getById(BOOK_DTO.getId())).willReturn(Optional.of(BOOK_DTO));
        given(repository.save(any(Book.class))).willReturn(BOOK);
        given(authorService.findFirstByBooksId(BOOK.getId())).willReturn(AUTHOR);
        given(genreService.findFirstByBooksId(BOOK.getId())).willReturn(GENRE);

        BookDto actualBook = service.edit(BOOK_DTO.getId(), BOOK_DTO.getName());
        assertThat(actualBook).isEqualTo(BOOK_DTO);
    }

    @DisplayName("возвращать ожидаемый список книг для автора ")
    @Test
    void shouldReturnExpectedAuthorBooksCount() {
        BookDto book1 = new BookDto("507f191e810c19729de860ea", "Book1", AUTHOR_DTO, GENRE_DTO);
        BookDto book2 = new BookDto("607f191e810c19729de860ea", "Book2", AUTHOR_DTO, GENRE_DTO);

        Author author = new Author(AUTHOR_DTO.getId(), AUTHOR_DTO.getFirstName(), AUTHOR_DTO.getLastName(),
                Set.of(dtoToBook(book1), dtoToBook(book2)));

        given(repository.getBooks(Set.of(book1.getId(), book2.getId()))).willReturn(List.of(book1, book2));
        given(authorService.findById(AUTHOR.getId())).willReturn(author);

        List<BookDto> actualBooks = service.getByAuthorId(AUTHOR.getId());
        Assertions.assertThat(actualBooks).usingFieldByFieldElementComparator().containsExactly(book1, book2);
    }

    @DisplayName("возвращать ожидаемый список книг для жанра ")
    @Test
    void shouldReturnExpectedGenreBooksCount() {
        Genre genre = new Genre(GENRE_DTO.getId(), GENRE_DTO.getName(), Set.of(dtoToBook(BOOK_DTO)));

        given(repository.getBooks(Set.of(BOOK_DTO.getId()))).willReturn(List.of(BOOK_DTO));
        given(genreService.findById(GENRE.getId())).willReturn(genre);

        List<BookDto> actualBooks = service.getByGenreId(GENRE.getId());
        Assertions.assertThat(actualBooks).usingFieldByFieldElementComparator().containsExactly(BOOK_DTO);
    }

    private static Book dtoToBook(BookDto dto) {
        return new Book(dto.getId(), dto.getName(), new ArrayList<>());
    }
}