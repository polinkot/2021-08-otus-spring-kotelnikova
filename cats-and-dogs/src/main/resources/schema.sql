drop table if exists cat;
drop table if exists dog;
drop table if exists owner;
drop table if exists adoption;
drop table if exists volunteer;

create table cat(id bigserial, name varchar(255) not null, PRIMARY KEY(id));

create table dog(id bigserial, name varchar(255) not null, PRIMARY KEY(id));

create table owner(id bigserial, PRIMARY KEY(id));

create table adoption(id bigserial, PRIMARY KEY(id));

create table volunteer(id bigserial, PRIMARY KEY(id));
