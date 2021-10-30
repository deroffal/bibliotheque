package fr.deroffal.bibliotheque.authentification.domain.service;

import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;

public interface UserRepositoryAdapter {

    Optional<UserDto> findByLogin(final String login);

    boolean existsByLogin(final String login);

    UserDto create(final UserDto userDto);
}
