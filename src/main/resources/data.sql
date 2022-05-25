CREATE SEQUENCE IF NOT EXISTS seq;

DROP TABLE IF EXISTS books;

CREATE TABLE books (

id LONG NOT NULL DEFAULT nextval('seq') PRIMARY KEY,
title VARCHAR(35) NOT NULL,
author VARCHAR(35) NOT NULL,
description VARCHAR(500) NOT NULL,
link VARCHAR(150) NOT NULL,
category VARCHAR(35) NOT NULL,
year VARCHAR(4) NOT NULL,
available BIT(1) NOT NULL

);

insert into books (id, title, author, description, link, category, year, available)
values (nextval('seq'), 'Clean Code', 'Robert C. Martin',
        'Even bad code can function. But if code is not clean, it can bring a development organization to its knees.',
        'https://www.oreilly.com/library/view/clean-code-a/9780136083238/', 'education', '2008', 1),
        (nextval('seq'), 'Code Complete', 'Steve McConnell',
        'Widely considered one of the best practical guides to programming, Steve McConnell''s original CODE COMPLETE has been helping developers write better software for more than a decade.',
        'https://www.oreilly.com/library/view/code-complete-2nd/0735619670/', 'education', '2005', 1),
        (nextval('seq'), 'Refactoring', 'Martin Fowler',
        'Fully Revised and Updated Includes New Refactorings and Code Examples',
        'https://www.oreilly.com/library/view/refactoring-improving-the/9780134757681/', 'education', '2018', 1),
         (nextval('seq'), 'Design Patterns', 'Eric Freeman',
        'With Design Patterns, you get to take advantage of the best practices and experience of others so you can spend your time on something more challenging',
        'https://www.oreilly.com/library/view/head-first-design/9781492077992/', 'education', '2020', 0),
         (nextval('seq'), 'Grokking Algorithms', 'Aditya Bhargava',
        'Grokking Algorithms is a friendly take on this core computer science topic',
        'https://www.oreilly.com/library/view/grokking-algorithms/9781617292231/', 'education', '2016', 1);

