CREATE SCHEMA portail;

CREATE SEQUENCE portail.seq_role
  INCREMENT BY 1
  START WITH 1;

CREATE SEQUENCE portail.seq_utilisateur
  INCREMENT BY 1
  START WITH 1;

CREATE SEQUENCE portail.seq_utilisateur_role
  INCREMENT BY 1
  START WITH 1;

CREATE TABLE portail.role(
 id BIGINT PRIMARY KEY NOT NULL,
 role_name VARCHAR(100) NOT NULL
);

CREATE TABLE portail.utilisateur(
 id BIGINT PRIMARY KEY NOT NULL,
 login VARCHAR(100) NOT NULL,
 password VARCHAR(100) NOT NULL
);


CREATE TABLE portail.utilisateur_role(
 id BIGINT PRIMARY KEY NOT NULL,
 user_id BIGINT NOT NULL,
 role_id BIGINT NOT NULL
);