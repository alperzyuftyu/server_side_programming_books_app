create table book
(
    id           integer      not null GENERATED ALWAYS AS IDENTITY,
    author       varchar(255) not null,
    price_in_eur integer check (price_in_eur <= 200 AND price_in_eur >= 0),
    title        varchar(255) not null,
    publication_year integer check (publication_year <= 2100 AND publication_year >= 1000),
    description  varchar(1000),
    language     varchar(255),
    primary key (id)
);
create table member
(
    id       integer      not null GENERATED ALWAYS AS IDENTITY,
    username varchar(255) not null,
    email    varchar(255),
    primary key (id)
);
create table genre
(
    id   integer      not null GENERATED ALWAYS AS IDENTITY,
    name varchar(255) not null,
    primary key (id)
);

create table users (
    username varchar(50) not null primary key,
    password varchar(100) not null,
    enabled boolean not null
);

create table authorities (
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users foreign key (username) references users(username)
);

create unique index ix_auth_username on authorities (username, authority);