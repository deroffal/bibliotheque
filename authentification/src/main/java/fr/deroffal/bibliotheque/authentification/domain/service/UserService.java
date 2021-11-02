package fr.deroffal.bibliotheque.authentification.domain.service;

import java.util.Optional;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepositoryAdapter userRepositoryAdapter;

    public Optional<UserDto> getByUsername(final String username) {
        return userRepositoryAdapter.findByUsername(username);
    }

    public boolean existsByLogin(final String login) {
        return userRepositoryAdapter.existsByUsername(login);
    }

    public UserDto create(final UserDto userDto) {
        return userRepositoryAdapter.create(userDto);
    }
}
