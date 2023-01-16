DROP TABLE IF EXISTS customer;

CREATE TABLE customers
(
    id   INT PRIMARY KEY AUTO_INCREMENT NOT NUll,
    name VARCHAR(30),
    age  INT,
    city VARCHAR(30)
);

INSERT INTO customers(name, age, city)
values ('sam', 10, 'la'),
       ('mike', 10, 'lv'),
       ('jake', 10, 'sa'),
       ('chris', 10, 'ny')
