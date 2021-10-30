package fr.deroffal.bibliotheque.authentification.adapter.controller;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.deroffal.bibliotheque.authentification.application.UserAlreadyExistsException;
import fr.deroffal.bibliotheque.authentification.application.UserNotFoundException;
import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import fr.deroffal.bibliotheque.authentification.domain.service.UserService;
import fr.deroffal.bibliotheque.authentification.securite.JwtTokenService;
import fr.deroffal.bibliotheque.authentification.securite.details.JwtUserDetails;
import fr.deroffal.bibliotheque.commons.exception.ExceptionMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("getByLogin : L'utilisateur n'existe pas.")
    void getUserByLoginRetourne404QuandLoginInconnu() throws Exception {
        final String login = "toto";
        Mockito.doThrow(new UserNotFoundException(login)).when(userService).getByLogin(login);

        mockMvc.perform(
            get("/user/" + login).header("Authorization", generateHeader())
        ).andExpect(
            status().isNotFound()
        );
    }

    @Test
    @DisplayName("getByLogin : L'utilisateur existe.")
    void getUserByLoginRetourne200EtUserQuandLoginConnu() throws Exception {
        final String login = "toto";
        final UserDto expectedUser = new UserDto(35L, login, "clm#^`|'!(,;''rt321201'", null);
        when(userService.getByLogin(login)).thenReturn(Optional.of(expectedUser));

        mockMvc.perform(
                get("/user/" + login).header("Authorization", generateHeader())
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(expectedUser.id().intValue())))
            .andExpect(jsonPath("$.login", is(expectedUser.login())))
            .andExpect(jsonPath("$.password", is(expectedUser.password()))
            );
    }

    @Test
    @DisplayName("getByLogin : Erreur interne.")
    void getUserByLoginRetourne500QuandErreurInterne() throws Exception {
        final String login = "toto";
        doThrow(new NullPointerException("Ça a pété quelque part!")).when(userService).getByLogin(login);

        final MvcResult mvcResult = mockMvc.perform(
                get("/user/" + login).accept(APPLICATION_JSON).header("Authorization", generateHeader())
        )
            .andExpect(status().isInternalServerError())
            .andReturn();

        ExceptionMessage em = objectMapper.readValue(mvcResult.getResponse().getContentAsString(UTF_8), ExceptionMessage.class);
        assertEquals("/user/toto", em.getUri());
        final String message = em.getMessage();
        assertEquals("Une erreur interne est survenue : Ça a pété quelque part!", message);
    }

    @Test
    @DisplayName("create : L'utilisateur existe déjà.")
    void createUserRetourne409QuandDejaExistant() throws Exception {
        final String login = "toto";
        final UserDto userToCreate = new UserDto(null, login, null, null);

        Mockito.doThrow(new UserAlreadyExistsException("login")).when(userService).create(any());

        final String userJson = objectMapper.writeValueAsString(userToCreate);

        final MvcResult mvcResult = mockMvc.perform(
                post("/user/").content(userJson).contentType(APPLICATION_JSON).header("Authorization", generateHeader())
        )
                .andExpect(status().isConflict())
                .andReturn();

        assertEquals(0, mvcResult.getResponse().getContentLength());
    }

    @Test
    @DisplayName("create : L'utilisateur n'existe pas encore.")
    void createUserRetourne201QuandCreation() throws Exception {
        final String login = "toto";
        when(userService.getByLogin(login)).thenReturn(null);

        final UserDto userToCreate = new UserDto(null, login, null, null);
        final String userJson = objectMapper.writeValueAsString(userToCreate);

        final UserDto newUser = new UserDto(1L, login, null, null);
        when(userService.create(argThat(sameLoginThan(newUser)))).thenReturn(newUser);

        mockMvc.perform(
                post("/user/").content(userJson).contentType(APPLICATION_JSON).header("Authorization", generateHeader())
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(newUser.id().intValue())))
            .andExpect(jsonPath("$.login", is(newUser.login())))
            .andReturn();
    }

    private static ArgumentMatcher<UserDto> sameLoginThan(final UserDto newUser) {
        return userDto -> userDto.login().equals(newUser.login());
    }

    //Génère un header pour admin/admin
    private String generateHeader() {
        return "Bearer " + jwtTokenService.generateToken(new JwtUserDetails("admin", "$2a$10$3AoDzKHV.ExSwFXq8SPjK.3qSozxVVngcB0Xd4iAQcVlvz4yBgh1e"));
    }

}