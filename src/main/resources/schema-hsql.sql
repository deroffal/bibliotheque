CREATE SCHEMA authentification;

CREATE SEQUENCE authentification.seq_role
  INCREMENT BY 1
  START WITH 1;

CREATE SEQUENCE authentification.seq_utilisateur
  INCREMENT BY 1
  START WITH 1;

CREATE SEQUENCE authentification.seq_utilisateur_role
  INCREMENT BY 1
  START WITH 1;

CREATE TABLE authentification.role_applicatif(
 id BIGINT PRIMARY KEY NOT NULL,
 role_name VARCHAR(100) NOT NULL
);

CREATE TABLE authentification.utilisateur(
 id BIGINT PRIMARY KEY NOT NULL,
 login VARCHAR(100) NOT NULL,
 encoded_password VARCHAR(100) NOT NULL
);

CREATE TABLE authentification.utilisateur_role(
 id BIGINT PRIMARY KEY NOT NULL,
 user_id BIGINT NOT NULL,
 role_id BIGINT NOT NULL
);