create table if not exists supplier
(
    id             int auto_increment
        primary key,
    name           varchar(255) not null,
    address        varchar(255) null,
    country        varchar(128) not null,
    contact_person varchar(255) not null,
    email          varchar(255) null,
    phone_number   varchar(255) null,
    description    text         null,
    last_used      date         null
);
create table if not exists customer
(
    id             int auto_increment
        primary key,
    name           varchar(255) not null,
    address        varchar(255) null,
    country        varchar(128),
    contact_person varchar(255) null,
    email          varchar(255) null,
    phone_number   varchar(255) null,
    description    text         null
);

alter table product
    add constraint product_supplier_id_fk
        foreign key (supplier_id) references supplier (id);
