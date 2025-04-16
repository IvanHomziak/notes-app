CREATE DATABASE securenotes;

USE securenotes;

CREATE TABLE notes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content VARCHAR(255) NOT NULL,
    username TEXT NOT NULL
);