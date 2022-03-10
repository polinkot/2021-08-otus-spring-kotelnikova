
create table IF NOT EXISTS animal(id bigserial,
    name varchar(255) not null,
    gender varchar(10),
    age int,
    sterilized boolean not null default false,
    vaccinated boolean not null default false,
    status varchar(30) not null default 'NOT_ADOPTED',
    type varchar(10) not null,
    PRIMARY KEY(id));

create table IF NOT EXISTS owner(id bigserial,
    name varchar(255) not null,
    age int,
    address varchar(255) not null,
    phone varchar(255) not null,
    PRIMARY KEY(id));

create table IF NOT EXISTS adoption(id bigserial,
    animal_id bigint,
    owner_id bigint,
    PRIMARY KEY(id),
    FOREIGN KEY (animal_id) REFERENCES animal (id),
    FOREIGN KEY (owner_id) REFERENCES owner (id)
);

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


-- INSERT INTO "user" (id, username, password, full_name, phone)
--   values (1, 'user', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', 'User User', '89105558833'),
--          (2, 'admin', '$2a$10$YJM9Yq0rYD2kHPzr9.6e9OzbOScIIQIDzBrAAgmWVEk5tfvLOlSiS', 'Admin Admin', '89109992244');
--
-- INSERT INTO authority (id, user_id, authority)
--   values (1, 1, 'ROLE_USER'), (2, 2, 'ROLE_ADMIN');
