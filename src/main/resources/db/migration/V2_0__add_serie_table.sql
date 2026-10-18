create table serie
(
    id integer not null GENERATED ALWAYS AS IDENTITY,
    name varchar(255) not null,
    description varchar(1000),
    primary key (id)
);
alter table book add column serie_id integer;
alter table book add column sequence_number integer;
alter table book add constraint fk_book_serie foreign key (serie_id) references serie(id);