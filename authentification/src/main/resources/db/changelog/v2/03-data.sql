INSERT INTO role_applicatif(id, role_name) VALUES (1,'ADMIN');
INSERT INTO role_applicatif(id, role_name) VALUES (2,'USER');
-- --
--Mots de passe crypté par BCryptPasswordEncoder (en clair, il s'agit des logins)
INSERT INTO utilisateur(id, username, encoded_password) values (1, 'admin', '$2a$10$3AoDzKHV.ExSwFXq8SPjK.3qSozxVVngcB0Xd4iAQcVlvz4yBgh1e');
INSERT INTO utilisateur(id, username, encoded_password) values (2, 'user1', '$2a$10$6joC/UrDxKR4CZtkDEOi9uovbsM7nt1RA7TsFczudmr7Ir5dHPImK');
INSERT INTO utilisateur(id, username, encoded_password) values (3, 'user2', '$2a$10$IvID3zGmRTLpIB/uCnjxleEmk0hUe6Gyr9oKX6UqAZkWrb6xvrmvC');

INSERT INTO utilisateur_role(user_id,role_id) VALUES (1,1);
INSERT INTO utilisateur_role(user_id,role_id) VALUES (1,2);
INSERT INTO utilisateur_role(user_id,role_id) VALUES (2,2);
INSERT INTO utilisateur_role(user_id,role_id) VALUES (3,2);
COMMIT;
