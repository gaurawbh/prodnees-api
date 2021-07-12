create table if not exists nees_doctype
(
    id             int auto_increment
        primary key,
    name           varchar(100)     not null,
    description    text             null,
    sub_types_json json             null,
    active         bit default b'1' null,
    sys            bit default b'1' null
);

create table if not exists product
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    description text         null
);

create table if not exists batch
(
    id           int auto_increment
        primary key,
    product_id   int                      not null,
    name         varchar(255)             not null,
    description  text                     null,
    created_date datetime                 null,
    state        varchar(255) default '0' not null,
    start_date   date                     null,
    constraint batch_ibfk_1
        foreign key (product_id) references product (id)
            on update cascade
);

create table if not exists raw_product
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    description text         null
);

create table if not exists stage
(
    id          int auto_increment
        primary key,
    batch_id    int                         not null,
    name        varchar(255)                not null,
    description text                        null,
    indx        int                         null,
    state       varchar(100) default 'OPEN' not null,
    constraint stage_ibfk_1
        foreign key (batch_id) references batch (id)
            on update cascade on delete cascade
);

create table if not exists stage_raw_product
(
    stage_id       int not null,
    raw_product_id int not null,
    constraint stage_raw_product_ibfk_1
        foreign key (stage_id) references stage (id)
            on update cascade on delete cascade,
    constraint stage_raw_product_ibfk_2
        foreign key (raw_product_id) references raw_product (id)
            on update cascade
);

create table if not exists stage_reminder
(
    id          int auto_increment
        primary key,
    stage_id    int              not null,
    stage_state varchar(100)     not null,
    recipients  text             not null,
    message     text             not null,
    sender      varchar(255)     not null,
    sent        bit default b'0' null,
    constraint stage_reminder_ibfk_1
        foreign key (stage_id) references stage (id)
            on update cascade on delete cascade
);

create table if not exists stage_todo
(
    id          int auto_increment
        primary key,
    batch_id    int              not null,
    stage_id    int              not null,
    name        varchar(255)     not null,
    description text             null,
    complete    bit default b'0' null,
    constraint stage_todo_uniq
        unique (stage_id, name),
    constraint stage_todo_ibfk_1
        foreign key (batch_id) references batch (id)
            on update cascade on delete cascade,
    constraint stage_todo_ibfk_2
        foreign key (stage_id) references stage (id)
            on update cascade on delete cascade
);

create table if not exists user_attributes
(
    user_id      int          not null
        primary key,
    role         varchar(100) not null,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    email        varchar(255) not null,
    phone_number varchar(50)  null,
    address      varchar(255) null,
    constraint email
        unique (email)
);

create table if not exists associate_invitation
(
    invitor_id      int              not null,
    invitor_email   varchar(255)     not null,
    invitor_comment varchar(255)     null,
    invitee_id      int              not null,
    invitee_email   varchar(255)     not null,
    invitee_comment varchar(255)     null,
    accepted        bit default b'0' null,
    action          varchar(100)     not null,
    invitation_date date             not null,
    primary key (invitor_id, invitee_id),
    constraint associate_invitation_ibfk_1
        foreign key (invitor_id) references user_attributes (user_id)
            on update cascade on delete cascade,
    constraint associate_invitation_ibfk_2
        foreign key (invitor_email) references user_attributes (email)
            on update cascade on delete cascade,
    constraint associate_invitation_ibfk_3
        foreign key (invitee_id) references user_attributes (user_id)
            on update cascade on delete cascade,
    constraint associate_invitation_ibfk_4
        foreign key (invitor_email) references user_attributes (email)
            on update cascade on delete cascade
);

create table if not exists associates
(
    admin_id        int          not null,
    associate_id    int          not null,
    admin_email     varchar(255) not null,
    associate_email varchar(255) not null,
    started_date    date         null,
    primary key (admin_id, associate_id),
    constraint associates_all_unq
        unique (admin_email, admin_id, associate_id, associate_email),
    constraint associates___ibfk_3
        foreign key (admin_email) references user_attributes (email)
            on update cascade on delete cascade,
    constraint associates___ibfk_4
        foreign key (associate_email) references user_attributes (email)
            on update cascade on delete cascade,
    constraint associates_ibfk_1
        foreign key (admin_id) references user_attributes (user_id)
            on update cascade on delete cascade,
    constraint associates_ibfk_2
        foreign key (associate_id) references user_attributes (user_id)
            on update cascade on delete cascade
);

create table if not exists batch_right
(
    batch_id     int          not null,
    user_id      int          not null,
    object_right varchar(255) not null,
    primary key (batch_id, user_id),
    constraint batch_right_ibfk_1
        foreign key (batch_id) references batch (id)
            on update cascade on delete cascade,
    constraint batch_right_ibfk_2
        foreign key (user_id) references user_attributes (user_id)
            on update cascade on delete cascade
);

create table if not exists inspection_type
(
    id              int auto_increment
        primary key,
    name            varchar(255) not null,
    summary         text         null,
    created_by      int          null,
    created_by_name varchar(255) null,
    constraint name
        unique (name),
    constraint inspection_type_ibfk_1
        foreign key (created_by) references user_attributes (user_id)
            on update cascade on delete set null
);

create table if not exists nees_doc
(
    id                int auto_increment
        primary key,
    number            varchar(100)                           not null,
    name              varchar(255)                           not null,
    description       text                                   null,
    doc_type          varchar(100)                           null,
    doc_sub_type      varchar(100)                           null,
    mime_content_type varchar(100) default 'application/pdf' not null,
    object_type       varchar(100)                           null,
    object_id         int                                    null,
    created_by        int                                    null,
    last_modified_by  int                                    null,
    deleted           bit          default b'0'              not null,
    created_datetime  datetime     default CURRENT_TIMESTAMP not null,
    modified_datetime datetime     default CURRENT_TIMESTAMP not null,
    constraint nees_doc_user_attributes_user_id_fk
        foreign key (created_by) references user_attributes (user_id)
            on update cascade on delete set null,
    constraint nees_doc_user_attributes_user_id_fk_2
        foreign key (last_modified_by) references user_attributes (user_id)
);

create table if not exists document_right
(
    user_id      int          not null,
    document_id  int          not null,
    object_right varchar(100) not null,
    primary key (user_id, document_id),
    constraint document_right_ibfk_1
        foreign key (user_id) references user_attributes (user_id)
            on update cascade on delete cascade,
    constraint document_right_ibfk_2
        foreign key (document_id) references nees_doc (id)
            on update cascade on delete cascade
);

create table if not exists nees_file
(
    doc_id            int                                    not null
        primary key,
    mime_content_type varchar(100) default 'application/pdf' not null,
    file_name         varchar(255)                           not null,
    file              longblob                               not null,
    constraint nees_file_ibfk_1
        foreign key (doc_id) references nees_doc (id)
            on update cascade on delete cascade
);

create table if not exists nees_object_right
(
    user_id      int          not null,
    nees_object  varchar(100) not null,
    object_right varchar(100) not null,
    primary key (user_id, nees_object),
    constraint nees_object_right_ibfk_1
        foreign key (user_id) references user_attributes (user_id)
            on update cascade on delete cascade
);

create table if not exists product_right
(
    product_id   int          not null,
    user_id      int          not null,
    object_right varchar(255) not null,
    primary key (product_id, user_id),
    constraint product_right_ibfk_1
        foreign key (product_id) references product (id)
            on update cascade on delete cascade,
    constraint product_right_ibfk_2
        foreign key (user_id) references user_attributes (user_id)
            on update cascade on delete cascade
);

