insert into genre (id, `name`) values (1, 'Fantasy');
insert into genre (id, `name`) values (2, 'Deletable');

insert into author (id, first_name, last_name) values (1, 'Petr', 'Petrov');
insert into author (id, first_name, last_name) values (2, 'Deletable', 'Deletable');

insert into book (id, `name`, author_id, genre_id) values (1, 'Hobbit', 1, 1);