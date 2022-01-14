drop table if exists genre;
create table genre(id bigserial, name varchar(255) not null unique);

drop table if exists author;
create table author(id bigserial, first_name varchar(255) not null, last_name varchar(255) not null);

drop table if exists book;
create table book(
    id bigserial,
    name varchar(255) not null,
    author_id bigint not null,
    genre_id bigint not null,
    foreign key (author_id) references author(id) on delete cascade,
    foreign key (genre_id) references genre(id) on delete cascade
);

drop table if exists comment;
create table comment(
    id bigserial,
    text varchar(255) not null,
    time datetime default current_timestamp,
    book_id bigint not null,
    foreign key (book_id) references book(id) on delete cascade
);

drop table if exists user;
CREATE TABLE user (
  id bigserial,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  full_name VARCHAR(100),
  email VARCHAR(100),
  enabled boolean NOT NULL DEFAULT true
);

drop table if exists authority;
CREATE TABLE authority (
  id bigserial,
  user_id bigint NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE UNIQUE INDEX ix_auth_user
  on authority (user_id, authority);
