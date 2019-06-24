package fr.deroffal.portail.api.authentification.utilisateur;

import fr.deroffal.portail.api.authentification.utilisateur.exception.UserAlreadyExistsException;
import fr.deroffal.portail.api.authentification.utilisateur.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserDao dao;

	@Autowired
	private UserMapper userMapper;

	public UserDto getByLogin(final String login) {
		final UserEntity user = dao.findByLogin(login);
		if (user == null) {
			throw new UserNotFoundException(login);
		}
		return userMapper.toDto(user);
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
