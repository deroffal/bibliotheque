package fr.deroffal.bibliotheque.api.authentification.utilisateur;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    @InjectMocks
    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Mapping Entity -> DTO.")
    void toDto() {
        final UserEntity entity = new UserEntity();
        final Long id = 35L;
        entity.setId(id);
        final String login = "login";
        entity.setLogin(login);
        final String password = "azerty";
        entity.setPassword(password);

        RoleEntity role = new RoleEntity();
        role.setId(1L);
        role.setRole("CHEF");
        entity.setRoles(List.of(role));

        final UserDto dto = mapper.toDto(entity);

        assertEquals(id, dto.getId());
        assertEquals(login, dto.getLogin());
        assertEquals(password, dto.getPassword());
        assertEquals(List.of(role.getRole()), dto.getRoles());
    }

    @Test
    @DisplayName("Mapping Entity -> DTO. Cas sans rôle")
    void toDto_sansRole() {
        final UserEntity entity = new UserEntity();
        final Long id = 35L;
        entity.setId(id);
        final String login = "login";
        entity.setLogin(login);
        final String password = "azerty";
        entity.setPassword(password);

        final UserDto dto = mapper.toDto(entity);

        assertEquals(id, dto.getId());
        assertEquals(login, dto.getLogin());
        assertEquals(password, dto.getPassword());
    }

    @Test
    @DisplayName("Mapping DTO -> Entity.")
    void toEntityAndEncorePassword() {
        MockitoAnnotations.initMocks(this);

        final UserDto dto = new UserDto();
        final String login = "login";
        dto.setLogin(login);
        final String password = "azerty";
        dto.setPassword(password);

        final String encodedPassword = "wxcvbn";
        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        final UserEntity entity = mapper.toEntityAndEncorePassword(dto);
        assertNull(entity.getId());
        assertNull(entity.getRoles());

        assertEquals(login, entity.getLogin());
        assertEquals(encodedPassword, entity.getPassword());
    }

    @Test
    @DisplayName("Test des cas où l'entrée est null.")
    void testNullInput() {
        assertNull(mapper.toDto(null));
        assertNull(mapper.toEntityAndEncorePassword(null));
    }
}
