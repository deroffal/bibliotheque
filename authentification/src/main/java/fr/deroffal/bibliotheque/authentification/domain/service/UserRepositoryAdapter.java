package fr.deroffal.bibliotheque.authentification.domain.service;

import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;

public interface UserRepositoryAdapter {

    Optional<UserDto> findByUsername(final String username);

    boolean existsByUsername(final String username);

    UserDto create(final UserDto userDto);
}
