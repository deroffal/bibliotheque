create table utilisateur_role
(
    user_id bigint not null
        constraint fkg3pnkokyynkwptthli4kix8xs
            references utilisateur,
    role_id bigint not null
        constraint fkd5v2bdog3wl0fxnlhnucxoxjl
            references role_applicatif
);
