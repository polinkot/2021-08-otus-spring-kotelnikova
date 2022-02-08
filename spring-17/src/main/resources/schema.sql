drop table if exists comment;
drop table if exists book;
drop table if exists author;
drop table if exists genre;

create table genre(id bigserial, name varchar(255) not null unique, PRIMARY KEY(id));

create table author(id bigserial, first_name varchar(255) not null, last_name varchar(255) not null, PRIMARY KEY(id));

create table book(id bigserial,
    name varchar(255) not null,
    author_id bigint not null,
    genre_id bigint not null,
    PRIMARY KEY(id),
    CONSTRAINT fk_author
      FOREIGN KEY(author_id)
	  REFERENCES author(id) on delete cascade,
	CONSTRAINT fk_genre
      FOREIGN KEY(genre_id)
	  REFERENCES genre(id) on delete cascade
);

create table comment(id bigserial,
    text varchar(255) not null,
    time timestamp default current_timestamp,
    book_id bigint not null,
    PRIMARY KEY(id),
    CONSTRAINT fk_book
      FOREIGN KEY(book_id)
	  REFERENCES book(id) on delete cascade
  );
