
create table IF NOT EXISTS adoption(id bigserial,
    animal_id bigint,
    owner_id bigint,
    volunteer_id bigint,
    PRIMARY KEY(id),
    FOREIGN KEY (animal_id) REFERENCES animal (id),
    FOREIGN KEY (owner_id) REFERENCES owner (id)
);

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

CREATE TABLE IF NOT EXISTS "user" (
  id bigserial,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  full_name VARCHAR(100),
  phone VARCHAR(100),
  enabled boolean NOT NULL DEFAULT true,
  account_non_expired boolean NOT NULL DEFAULT true,
  account_non_locked boolean NOT NULL DEFAULT true,
  credentials_non_expired boolean NOT NULL DEFAULT true,
  PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS authority(
  id bigserial,
  user_id bigint NOT NULL,
  authority VARCHAR(50) NOT NULL,
  FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS ix_auth_user
ON authority(user_id, authority);
