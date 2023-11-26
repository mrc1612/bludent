CREATE TABLE users {

    id varchar(100) primary key unique not null,
    login varchar(100) not null unique,
    password varchar(100) not null,
    role varchar(100)  not null
};