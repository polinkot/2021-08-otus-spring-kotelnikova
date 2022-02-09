insert into genre (id, `name`)
values (1, 'Genre1'), (2, 'Genre2'), (3, 'Genre3');

insert into author (id, first_name, last_name)
values (1, 'AuthorF1', 'AuthorL1'), (2, 'AuthorF2', 'AuthorL2');

insert into book (id, `name`, author_id, genre_id)
values (1, 'Book1', 1, 2), (2, 'Book2', 1, 1);

insert into comment (id, text, book_id)
values (1, 'Comment1', 1), (2, 'Comment2', 1);
