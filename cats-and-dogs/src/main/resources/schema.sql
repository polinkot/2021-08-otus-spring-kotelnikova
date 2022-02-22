
create table IF NOT EXISTS adoption(id bigserial,
    animal_id bigint,
    owner_id bigint,
    volunteer_id bigint,
    PRIMARY KEY(id));

create table IF NOT EXISTS animal(id bigserial,
    name varchar(255) not null,
    gender varchar(10),
    age int,
    sterilized boolean not null default false,
    vaccinated boolean not null default false,
    type varchar(10) not null,
    PRIMARY KEY(id));

create table IF NOT EXISTS owner(id bigserial,
    name varchar(255) not null,
    age int,
    address varchar(255) not null,
    phone varchar(255) not null,
    PRIMARY KEY(id));

create table IF NOT EXISTS volunteer(id bigserial,
    name varchar(255) not null,
    phone varchar(255) not null,
    PRIMARY KEY(id));
