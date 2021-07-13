create table product_group
(
    id          int          not null auto_increment primary key,
    private_key varchar(100) not null,
    label       varchar(100) not null,
    description text,
    active      bit(1) default 1
)