create table member_favorite_books(
    member_id integer not null,
    favorite_books_id integer not null ,
    primary key (member_id,favorite_books_id),

    constraint fk_favorite_member
    foreign key (member_id)
    references member(id),

    constraint fk_favorite_book
        foreign key (favorite_books_id)
            references book(id)
);