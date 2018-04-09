INSERT INTO ROLE(ID,ROLE_NAME) VALUES (1,'ADMIN');
INSERT INTO ROLE(ID,ROLE_NAME) VALUES (2,'USER');

--Mots de passe crypté par BCryptPasswordEncoder (en clair, il s'agit des logins)
INSERT INTO UTILISATEUR(ID, LOGIN, PASSWORD) values (1, 'admin', '$2a$10$3AoDzKHV.ExSwFXq8SPjK.3qSozxVVngcB0Xd4iAQcVlvz4yBgh1e');
INSERT INTO UTILISATEUR(ID, LOGIN, PASSWORD) values (2, 'user1', '$2a$10$6joC/UrDxKR4CZtkDEOi9uovbsM7nt1RA7TsFczudmr7Ir5dHPImK');
INSERT INTO UTILISATEUR(ID, LOGIN, PASSWORD) values (3, 'user2', '$2a$10$IvID3zGmRTLpIB/uCnjxleEmk0hUe6Gyr9oKX6UqAZkWrb6xvrmvC');

INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (1,1);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (1,2);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (2,2);
INSERT INTO USER_ROLE(USER_ID,ROLE_ID) VALUES (3,2);
COMMIT;