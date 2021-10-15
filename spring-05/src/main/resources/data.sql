insert into genre (id, `name`) values (1, 'Novel');
insert into genre (id, `name`) values (2, 'Fantasy');

insert into author (id, first_name, last_name) values (1, 'John', 'Tolkien');
insert into author (id, first_name, last_name) values (2, 'Margaret', 'Mitchell');

insert into book (id, `name`, author_id, genre_id) values (1, 'Hobbit', 1, 2);
insert into book (id, `name`, author_id, genre_id) values (2, 'Gone with the Wind', 2, 1);
