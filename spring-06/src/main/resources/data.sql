insert into genre (id, `name`) values (1, 'Genre1');
insert into genre (id, `name`) values (2, 'Genre2');

insert into author (id, first_name, last_name) values (1, 'AuthorF1', 'AuthorL1');
insert into author (id, first_name, last_name) values (2, 'AuthorF2', 'AuthorL2');

insert into book (id, `name`, author_id, genre_id) values (1, 'Book1', 1, 2);
insert into book (id, `name`, author_id, genre_id) values (2, 'Book2', 2, 1);
