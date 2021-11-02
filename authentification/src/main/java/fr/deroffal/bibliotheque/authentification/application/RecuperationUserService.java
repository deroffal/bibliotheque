package fr.deroffal.bibliotheque.authentification.application;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import fr.deroffal.bibliotheque.authentification.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecuperationUserService {

    private final UserService userService;

    public UserDto getByLogin(final String login) {
        return userService.getByUsername(login).orElseThrow(() -> new UserNotFoundException(login));
    }
}
