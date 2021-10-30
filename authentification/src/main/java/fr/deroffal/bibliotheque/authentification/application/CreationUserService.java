package fr.deroffal.bibliotheque.authentification.application;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import fr.deroffal.bibliotheque.authentification.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreationUserService {

    private final UserService userService;

    public UserDto create(final UserDto user) {
        final String login = user.getLogin();
        if (userService.existsByLogin(login)) {
            throw new UserAlreadyExistsException(login);
        }
        return userService.create(user);
    }
}
