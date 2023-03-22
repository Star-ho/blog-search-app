create table IF NOT EXISTS SEARCH_KEYWORD
(
    ID        bigint auto_increment,
    KEYWORD   varchar(255) not null,
    HIT_COUNT bigint default 0 not null,
    constraint SEARCH_KEYWORD_pk
        primary key (ID),
    constraint SEARCH_KEYWORD_pk2
        unique (KEYWORD)
);


create table IF NOT EXISTS SEARCH_KEYWORD_LOG
(
    ID         bigint       not null,
    KEYWORD    varchar(255) not null,
    CREATED_AT DATETIME     not null,
    constraint SEARCH_KEYWORD_LOG_pk
        primary key (ID)
);

create table IF NOT EXISTS SEQUENCE
(
    NAME     varchar(50)      not null,
    SEQUENCE bigint default 0 not null,
    constraint SEQUENCE_pk
        primary key (NAME)
);

