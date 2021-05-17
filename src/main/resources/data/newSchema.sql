
create table ola_document
(
    id   int auto_increment
        primary key,
    name varchar(255) not null,
    file longblob     not null
);

create table ola_product
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    description text         null
);


create table ola_raw_product
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    description text         null
);


create table ola_user
(
    id       int auto_increment
        primary key,
    email    varchar(100)                not null,
    password varchar(200)                not null,
    role     varchar(50) default 'ADMIN' not null,
    enabled  tinyint(1)  default 0       not null,
    constraint username
        unique (email)
);






