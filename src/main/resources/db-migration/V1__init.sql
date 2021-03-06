CREATE SCHEMA IF NOT EXISTS MASTERS;

CREATE TABLE IF NOT EXISTS MASTERS.ACCOUNTS (
    USER_ID SERIAL PRIMARY KEY,
    USERNAME VARCHAR (50),
    PASSWORD VARCHAR (50),
    EMAIL VARCHAR (255),
    CREATED_ON TIMESTAMP NOT NULL,
    LAST_LOGIN TIMESTAMP);

CREATE TABLE IF NOT EXISTS MASTERS.AUTHOR (
  ID SERIAL PRIMARY KEY,
  FIRST_NAME VARCHAR(50),
  LAST_NAME VARCHAR(50) NOT NULL);

CREATE TABLE IF NOT EXISTS MASTERS.BOOK (
  ID SERIAL PRIMARY KEY,
  TITLE VARCHAR(100) NOT NULL);

CREATE TABLE IF NOT EXISTS MASTERS.AUTHOR_BOOK (
 AUTHOR_ID INT NOT NULL,
 BOOK_ID INT NOT NULL,
 PRIMARY KEY (AUTHOR_ID,
 BOOK_ID), CONSTRAINT FK_AB_AUTHOR
    FOREIGN KEY (AUTHOR_ID) REFERENCES MASTERS.AUTHOR (ID) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_AB_BOOK
    FOREIGN KEY (BOOK_ID) REFERENCES MASTERS.BOOK (ID));

-- INSERT INTO masters.author VALUES
--   (1, 'Kathy', 'Sierra'),
--   (2, 'Bert', 'Bates'),
--   (3, 'Bryan', 'Basham');
--
-- INSERT INTO masters.book VALUES
--   (1, 'Head First Java'),
--   (2, 'Head First Servlets and JSP'),
--   (3, 'OCA/OCP Java SE 7 Programmer');
--
-- INSERT INTO masters.author_book VALUES (1, 1), (1, 3), (2, 1);
-- INSERT INTO masters.accounts (username,"password",email,created_on,last_login) VALUES
-- ('sang','1234','sang@gmail.com','2021-11-02 10:51:12.903','2021-11-02 10:51:12.903')
-- ,('trong','4455','trong@gmail.com','2021-11-02 10:51:33.145','2021-11-02 10:51:33.145')
-- ;