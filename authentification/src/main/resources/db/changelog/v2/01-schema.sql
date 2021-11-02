create sequence seq_role;

alter sequence seq_role owner to postgres;

create sequence seq_utilisateur;

alter sequence seq_utilisateur owner to postgres;

create table role_applicatif
(
    id        bigint not null
        constraint role_applicatif_pkey
            primary key,
    role_name varchar(255)
);

alter table role_applicatif
    owner to postgres;

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

alter table utilisateur
    owner to postgres;

create table utilisateur_role
(
    user_id bigint not null
        constraint fkg3pnkokyynkwptthli4kix8xs
            references utilisateur,
    role_id bigint not null
        constraint fkd5v2bdog3wl0fxnlhnucxoxjl
            references role_applicatif
);

alter table utilisateur_role
    owner to postgres;

