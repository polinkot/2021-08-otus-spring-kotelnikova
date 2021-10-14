insert into genre (id, `name`) values (1, 'Sci-fi');

insert into author (id, first_name, last_name) values (1, 'Ivan', 'Ivanov');
insert into author (id, first_name, last_name) values (2, 'Petr', 'Petrov');

insert into book (id, `name`, author_id, genre_id) values (1, 'Hobbit', 1, 1);
