package fr.deroffal.bibliotheque.authentification.utilisateur;

import fr.deroffal.bibliotheque.authentification.utilisateur.exception.UserAlreadyExistsException;
import fr.deroffal.bibliotheque.authentification.utilisateur.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao dao;

    private final UserMapper userMapper;

    public UserDto getByLogin(final String login) {
        return dao.findByLogin(login)
            .map(userMapper::toDto)
            .orElseThrow(() ->new UserNotFoundException(login) );

    }

    public UserDto create(final UserDto user) {
        final String login = user.getLogin();
        if (dao.existsByLogin(login)) {
            throw new UserAlreadyExistsException(login);
        }
        final UserEntity userEntity = userMapper.toEntityAndEncorePassword(user);
        return userMapper.toDto(dao.save(userEntity));
    }
}
