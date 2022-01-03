create sequence seq_role;

create sequence seq_utilisateur;

create table role_applicatif
(
    id        bigint not null
        constraint role_applicatif_pkey
            primary key,
    role_name varchar(255)
);

create table utilisateur
(
    id               bigint       not null
        constraint utilisateur_pkey
            primary key,
    username            varchar(255) not null
        constraint uk_18vwp4resqussqmlpqnymfqxk
            unique,
    encoded_password varchar(255) not null
);
