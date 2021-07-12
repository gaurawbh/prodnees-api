create table company
(
    id              int auto_increment
        primary key,
    name            varchar(255)                 not null,
    active          bit          default b'0'    not null,
    country         varchar(100) default 'Nepal' null,
    zone_id         varchar(100)                 null,
    email           varchar(100)                 null,
    phone_number    varchar(100)                 null,
    address         varchar(255)                 null,
    last_active     datetime                     null,
    schema_instance varchar(100)                 null,
    currency        varchar(100)                 null,
    constraint name
        unique (name)
);

create table company_logo
(
    company_id int      not null
        primary key,
    logo       longblob null,
    constraint company_logo_ibfk_1
        foreign key (company_id) references company (id)
            on update cascade on delete cascade
);

create table document_test_table
(
    id           int auto_increment
        primary key,
    doc          longblob                          not null,
    content_type varchar(100) default 'image/jpeg' not null
);


create table public_customer_query
(
    id       int auto_increment
        primary key,
    name     varchar(100)     null,
    email    varchar(100)     not null,
    message  text             not null,
    resolved bit default b'0' null
);

create table user
(
    id                int auto_increment
        primary key,
    email             varchar(255)     not null,
    password          varchar(255)     not null,
    role              varchar(100)     not null,
    enabled           bit default b'0' not null,
    company_id        int              not null,
    schema_instance   varchar(100)     not null,
    first_name        varchar(100)     not null,
    last_name         varchar(100)     not null,
    constraint username_email
        unique (email),
    constraint user_ibfk_1
        foreign key (company_id) references company (id)
            on update cascade on delete cascade
);

create table blocked_jwt
(
    id                  int auto_increment
        primary key,
    user_id             int          not null,
    email               varchar(255) not null,
    jwt                 text         not null,
    created_datetime    datetime     null,
    jwt_expiry_datetime datetime     null,
    constraint blocked_jwt_ibfk_1
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

create index user_id
    on blocked_jwt (user_id);

create table forgot_password_info
(
    id                int auto_increment
        primary key,
    email             varchar(255)                       not null,
    password          varchar(255)                       not null,
    created_date_time datetime default CURRENT_TIMESTAMP null,
    constraint email
        unique (email),
    constraint forgot_password_info_ibfk_1
        foreign key (email) references user (email)
            on update cascade on delete cascade
);

create table jwt_tail
(
    user_id           int          not null
        primary key,
    email             varchar(255) not null,
    tail              varchar(255) not null,
    last_changed_date date         not null,
    constraint email
        unique (email),
    constraint jwt_tail_ibfk_1
        foreign key (user_id) references user (id)
            on update cascade on delete cascade,
    constraint jwt_tail_ibfk_2
        foreign key (email) references user (email)
            on update cascade on delete cascade
);

create table temp_password_info
(
    id                int auto_increment
        primary key,
    email             varchar(255)                       not null,
    created_date_time datetime default CURRENT_TIMESTAMP null,
    constraint email
        unique (email),
    constraint temp_password_info_ibfk_1
        foreign key (email) references user (email)
            on update cascade on delete cascade
);

create table user_avatar_image
(
    user_id      int      not null
        primary key,
    avatar_image longblob null,
    constraint user_avatar_image_ibfk_1
        foreign key (user_id) references user (id)
            on update cascade on delete cascade
);

