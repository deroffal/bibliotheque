insert into utilisateur(id, username, encoded_password)
values (1, 'admin', '$2a$10$3AoDzKHV.ExSwFXq8SPjK.3qSozxVVngcB0Xd4iAQcVlvz4yBgh1e'),
       (2, 'user1', '$2a$10$6joC/UrDxKR4CZtkDEOi9uovbsM7nt1RA7TsFczudmr7Ir5dHPImK'),
       (3, 'user2', '$2a$10$IvID3zGmRTLpIB/uCnjxleEmk0hUe6Gyr9oKX6UqAZkWrb6xvrmvC');

insert into role_applicatif(id, role_name)
values (1, 'ADMIN'),
       (2, 'USER');

insert into utilisateur_role(user_id, role_id)
values (1, 1),
       (1, 2),
       (2, 2),
       (3, 2);
