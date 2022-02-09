insert into genre (name)
values ('Genre1'), ('Genre2');

insert into author (first_name, last_name)
values ('AuthorF1', 'AuthorL1'), ('AuthorF2', 'AuthorL2');

insert into book (name, author_id, genre_id)
values ('Book1', 1, 2), ('Book2', 2, 1);

insert into comment (text, book_id)
values ('Comment1', 1), ('Comment2', 1);
