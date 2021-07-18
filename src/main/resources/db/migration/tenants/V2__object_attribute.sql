create table if not exists object_attribute
(
    id           int auto_increment primary key,
    private_key  varchar(128) not null,
    nees_object  varchar(128) not null,
    label        varchar(128) not null,
    help_content varchar(255)  null,
    value_type   varchar(128) not null,
    required     bit(1)       not null default 0,
    sys          bit(1)       not null default 1,
    unique (private_key, nees_object)
)