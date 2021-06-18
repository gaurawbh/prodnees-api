create table document
(
    id   int auto_increment
        primary key,
    name varchar(255) not null,
    file longblob     not null
);

create table product
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    description text         null
);

create table batch
(
    id           int auto_increment
        primary key,
    product_id   int                      not null,
    name         varchar(255)             not null,
    description  text                     null,
    created_date datetime                 null,
    state        varchar(255) default '0' not null,
    constraint batch_ibfk_1
        foreign key (product_id) references product (id)
            on update cascade
);

create index batch_product_ibfk_1
    on batch (product_id);

create table raw_product
(
    id          int auto_increment
        primary key,
    name        varchar(255) not null,
    description text         null
);

create table stage
(
    id          int auto_increment
        primary key,
    batch_id    int                         not null,
    name        varchar(255)                not null,
    description text                        null,
    indx        int                         null,
    status      varchar(100) default 'OPEN' not null,
    constraint stage_ibfk_1
        foreign key (batch_id) references batch (id)
            on update cascade on delete cascade
);

create index batch_product_id
    on stage (batch_id);

create table stage_raw_product
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

create index raw_product_id
    on stage_raw_product (raw_product_id);

create index state_id
    on stage_raw_product (stage_id);

create table stage_reminder
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

create index state_id
    on stage_reminder (stage_id);

create table stage_todo
(
    id          int auto_increment
        primary key,
    batch_id    int              not null,
    state_id    int              not null,
    name        varchar(255)     not null,
    description text             null,
    complete    bit default b'0' null,
    constraint stage_todo_uniq
        unique (state_id, name),
    constraint stage_todo_ibfk_1
        foreign key (batch_id) references batch (id)
            on update cascade on delete cascade,
    constraint stage_todo_ibfk_2
        foreign key (state_id) references stage (id)
            on update cascade on delete cascade
);

create index batch_id
    on stage_todo (batch_id);


create table associate_invitation
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

create index invitee_id
    on associate_invitation (invitee_id);

create index invitor_email
    on associate_invitation (invitor_email);

create table associates
(
    admin_id        int          not null,
    associate_id    int          not null,
    admin_email     varchar(255) not null,
    associate_email varchar(255) not null,
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

create index user_id
    on associates (associate_id);

create table batch_approval_document
(
    id             int auto_increment
        primary key,
    batch_id       int          not null,
    document_id    int          not null,
    stage          varchar(100) not null,
    approver_id    int          not null,
    approver_email varchar(255) not null,
    constraint batch_approval_document_ibfk_1
        foreign key (batch_id) references batch (id)
            on update cascade on delete cascade,
    constraint batch_approval_document_ibfk_2
        foreign key (document_id) references document (id),
    constraint batch_approval_document_ibfk_3
        foreign key (approver_id) references user_attributes (user_id)
            on update cascade on delete cascade
);

create index approver_id
    on batch_approval_document (approver_id);

create index batch_product_id
    on batch_approval_document (batch_id);

create index document_id
    on batch_approval_document (document_id);

create table batch_right
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

create index user_id
    on batch_right (user_id);


create table document_right
(
    user_id           int          not null,
    document_id       int          not null,
    object_right_type varchar(100) not null,
    primary key (user_id, document_id),
    constraint document_right_ibfk_1
        foreign key (user_id) references user_attributes (user_id)
            on update cascade on delete cascade,
    constraint document_right_ibfk_2
        foreign key (document_id) references document (id)
            on update cascade on delete cascade
);

create index document_id
    on document_right (document_id);


create table product_right
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

create index owner_id
    on product_right (user_id);

create table stage_approval_document
(
    id             int auto_increment
        primary key,
    stage_id       int          not null,
    document_id    int          not null,
    state          varchar(100) not null,
    approver_id    int          not null,
    approver_email varchar(255) not null,
    constraint stage_approval_document_ibfk_1
        foreign key (stage_id) references stage (id)
            on update cascade on delete cascade,
    constraint stage_approval_document_ibfk_2
        foreign key (document_id) references document (id),
    constraint stage_approval_document_ibfk_3
        foreign key (approver_id) references user_attributes (user_id)
            on update cascade on delete cascade
);

create index approver_id
    on stage_approval_document (approver_id);

create index document_id
    on stage_approval_document (document_id);

create index state_id
    on stage_approval_document (stage_id);

create table user_attributes
(
    user_id      int          not null
        primary key,
    first_name   varchar(255) not null,
    last_name    varchar(255) not null,
    email        varchar(255) not null,
    phone_number varchar(50)  null,
    address      varchar(255) null,
    constraint user_attributes_ibfk_1
        foreign key (user_id) references prodnees_auth.user (id)
            on update cascade on delete cascade
);

