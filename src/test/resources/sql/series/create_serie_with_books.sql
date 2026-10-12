INSERT INTO serie (id, name, description)
VALUES (
           1,
           'MaddAddam-trilogie',
           'Een speculatieve fictie trilogie van Margaret Atwood.'
       );

INSERT INTO serie (id, name, description)
VALUES (
           2,
           'Harry Potter',
           'De bekende fantasyreeks van J.K. Rowling.'
       );


INSERT INTO book (
    id,
    title,
    publication_year,
    description,
    language,
    serie_id,
    sequence_number
)
VALUES (
           1,
           'Oryx and Crake',
           2003,
           'Een speculatieve fictie roman van Margaret Atwood.',
           'ENGELS',
           1,
           1
       );

INSERT INTO book (
    id,
    title,
    publication_year,
    description,
    language,
    serie_id,
    sequence_number
)
VALUES (
           2,
           'The Year of the Flood',
           2009,
           'Het tweede boek uit de MaddAddam-trilogie.',
           'ENGELS',
           1,
           2
       );

INSERT INTO book (
    id,
    title,
    publication_year,
    description,
    language,
    serie_id,
    sequence_number
)
VALUES (
           3,
           'MaddAddam',
           2013,
           'Het derde boek uit de MaddAddam-trilogie.',
           'ENGELS',
           1,
           3
       );