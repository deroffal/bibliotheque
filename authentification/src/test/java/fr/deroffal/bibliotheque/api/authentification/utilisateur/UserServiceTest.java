package fr.deroffal.bibliotheque.api.authentification.utilisateur;

import fr.deroffal.bibliotheque.api.authentification.utilisateur.exception.UserAlreadyExistsException;
import fr.deroffal.bibliotheque.api.authentification.utilisateur.exception.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserDao userDao;

	@Mock
	private UserMapper userMapper;

	@Test
	@DisplayName("Charger un utilisateur : On trouve un utilisateur, on retourne le UserDetails correspondant.")
	void getByLoginRetourneUtilisateur() {
		final String login = "login";
		final UserEntity user = new UserEntity();
		user.setLogin(login);

		when(userDao.findByLogin(login)).thenReturn(user);
		final UserDto expectedUser = new UserDto();
		when(userMapper.toDto(user)).thenReturn(expectedUser);

		final UserDto actualUser = userService.getByLogin(login);
		assertEquals(expectedUser, actualUser);
	}

	@Test
	@DisplayName("Charger un utilisateur : Pas de user trouvé, on lève une exception.")
	void getByLoginLanceExceptionSiNonTrouve() {
		final String login = "login";

		when(userDao.findByLogin(login)).thenReturn(null);

		assertThrows(UserNotFoundException.class, () -> userService.getByLogin(login));
	}

	@Test
	@DisplayName("Création d'un utilisateur.")
	void createRetourneUtilisateurCree() {
		final UserDto userDtoToSave = new UserDto();
		final UserEntity userEntityToSave = new UserEntity();

		final UserEntity savedUserEntity = new UserEntity();
		final UserDto expectedUser = new UserDto();

		when(userMapper.toEntityAndEncorePassword(userDtoToSave)).thenReturn(userEntityToSave);
		when(userDao.save(userEntityToSave)).thenReturn(savedUserEntity);
		when(userMapper.toDto(savedUserEntity)).thenReturn(expectedUser);

		assertEquals(expectedUser, userService.create(userDtoToSave));
	}

	@Test
	@DisplayName("Création d'un utilisateur.")
	void createLanceExceptionSiLoginTrouveEnBase() {
		final String login = "login";
		final UserDto userDtoToSave = new UserDto();
		userDtoToSave.setLogin(login);

		when(userDao.existsByLogin(login)).thenReturn(true);

		assertThrows(UserAlreadyExistsException.class, () -> userService.create(userDtoToSave));
	}
}
