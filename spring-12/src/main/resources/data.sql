insert into genre (id, `name`)
values (1, 'Genre1'), (2, 'Genre2');

insert into author (id, first_name, last_name)
values (1, 'AuthorF1', 'AuthorL1'), (2, 'AuthorF2', 'AuthorL2');

insert into book (id, `name`, author_id, genre_id)
values (1, 'Book1', 1, 2), (2, 'Book2', 2, 1);

insert into comment (id, text, book_id)
values (1, 'Comment1', 1), (2, 'Comment2', 1);

INSERT INTO user (id, username, password, full_name, email)
  values (1, 'user', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'User User', 'user@mail.com'),
         (2, 'admin', '$2a$10$YJM9Yq0rYD2kHPzr9.6e9OzbOScIIQIDzBrAAgmWVEk5tfvLOlSiS', 'Admin Admin', 'admin@mail.com');

INSERT INTO authority (id, user_id, authority)
  values (1, 1, 'ROLE_USER'), (2, 2, 'ROLE_ADMIN');