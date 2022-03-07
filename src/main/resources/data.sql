CREATE SEQUENCE IF NOT EXISTS seq;

DROP TABLE IF EXISTS books;

CREATE TABLE books (

id LONG NOT NULL DEFAULT nextval('seq') PRIMARY KEY,
title VARCHAR(35) NOT NULL,
author VARCHAR(35) NOT NULL,
link VARCHAR(150) NOT NULL,
category VARCHAR(35) NOT NULL,
year VARCHAR(4) NOT NULL,
available BIT(1) NOT NULL

);

insert into books (id, title, author, link, category, year, available)
values (nextval('seq'), 'Clean Code', 'Robert C. Martin',
        'https://www.oreilly.com/library/view/clean-code-a/9780136083238/','education', '2008', 1),
        (nextval('seq'), 'Code Complete', 'Steve McConnell',
        'https://www.oreilly.com/library/view/code-complete-2nd/0735619670/', 'education', '2005', 1),
        (nextval('seq'), 'Refactoring', 'Martin Fowler',
        'https://www.oreilly.com/library/view/refactoring-improving-the/9780134757681/', 'education', '2018', 1),
         (nextval('seq'), 'Design Patterns', 'Eric Freeman',
        'https://www.oreilly.com/library/view/head-first-design/9781492077992/', 'education', '2020', 0),
         (nextval('seq'), 'Grokking Algorithms', 'Aditya Bhargava',
        'https://www.oreilly.com/library/view/grokking-algorithms/9781617292231/', 'education', '2016', 1);

