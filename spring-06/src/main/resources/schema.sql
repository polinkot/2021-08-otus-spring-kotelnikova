drop table if exists genre;
create table genre(id bigserial, name varchar(255) not null);

drop table if exists author;
create table author(id bigserial, first_name varchar(255) not null, last_name varchar(255) not null);

drop table if exists book;
create table book(id bigserial,
    name varchar(255) not null,
    author_id bigint,
    genre_id bigint,
    foreign key (author_id) references author(id),
    foreign key (genre_id) references genre(id)
)