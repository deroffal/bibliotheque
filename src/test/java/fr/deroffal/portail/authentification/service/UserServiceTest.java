package fr.deroffal.portail.authentification.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.deroffal.portail.authentification.dao.UserDao;
import fr.deroffal.portail.authentification.dto.UserDto;
import fr.deroffal.portail.authentification.entity.RoleEntity;
import fr.deroffal.portail.authentification.entity.UserEntity;
import fr.deroffal.portail.authentification.mapping.UserMapper;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserDao userDao;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Charger un utilisateur : Pas de user trouvé, on lève une exception.")
    void loadUserByUsername_leveUsernameNotFoundException_siPasDeResultat() {
        final String login = "login";

        when(userDao.findByLogin(login)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(login));
    }

    @Test
    @DisplayName("Charger un utilisateur : On trouve un utilisateur, on retourne le UserDetails correspondant.")
    void loadUserByUsername_retourneUserDetails_siResultat() {
        final UserEntity user = new UserEntity();
        user.setId(1L);
        final String login = "login";
        user.setLogin(login);
        final String password = "zyuatf!:l;mljhfomwizuf#w<z32120vw:!;,efli:zuj";
        user.setPassword(password);

        final RoleEntity role = new RoleEntity();
        role.setRole("SUPER_ADMIN");
        user.setRoles(List.of(role));

        when(userDao.findByLogin(login)).thenReturn(user);

        final UserDetails actualUser = userService.loadUserByUsername(login);
        assertEquals(login, actualUser.getUsername());
        assertEquals(password, actualUser.getPassword());

        final Collection<? extends GrantedAuthority> actualUserAuthorities = actualUser.getAuthorities();
        assertAll("Un seul rôle : SUPER_ADMIN",
                () -> assertEquals(1, actualUserAuthorities.size()),
                () -> assertEquals("ROLE_SUPER_ADMIN", actualUserAuthorities.iterator().next().getAuthority())
        );
    }

    @Test
    @DisplayName("Création d'un utilisateur.")
    void createUser() {
        final UserDto userDtoToSave = new UserDto();
        final UserEntity userEntityToSave = new UserEntity();

        final UserEntity savedUserEntity = new UserEntity();
        final UserDto expectedUser = new UserDto();

        when(userMapper.toEntityAndEncorePassword(userDtoToSave)).thenReturn(userEntityToSave);
        when(userDao.save(userEntityToSave)).thenReturn(savedUserEntity);
        when(userMapper.toDto(savedUserEntity)).thenReturn(expectedUser);

        assertEquals(expectedUser, userService.createUser(userDtoToSave));
    }
}
