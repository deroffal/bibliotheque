package fr.deroffal.bibliotheque.api.authentification.utilisateur;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.deroffal.bibliotheque.api.authentification.utilisateur.exception.UserAlreadyExistsException;
import fr.deroffal.bibliotheque.api.authentification.utilisateur.exception.UserNotFoundException;
import fr.deroffal.bibliotheque.api.exception.ExceptionMessage;
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

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("getByLogin : L'utilisateur n'existe pas.")
    void getUserByLoginRetourne404QuandLoginInconnu() throws Exception {
        final String login = "toto";
        Mockito.doThrow(new UserNotFoundException(login)).when(userService).getByLogin(login);

        mockMvc.perform(
                get("/user/" + login))
                .andExpect(status().isNotFound()
                );
    }

    @Test
    @DisplayName("getByLogin : L'utilisateur existe.")
    void getUserByLoginRetourne200EtUserQuandLoginConnu() throws Exception {
        final String login = "toto";
        final UserDto expectedUser = new UserDto();
        expectedUser.setId(35L);
        expectedUser.setLogin(login);
        expectedUser.setPassword("clm#^`|'!(,;''rt321201'");
        when(userService.getByLogin(login)).thenReturn(expectedUser);

        mockMvc.perform(
                get("/user/" + login)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedUser.getId().intValue())))
                .andExpect(jsonPath("$.login", is(expectedUser.getLogin())))
                .andExpect(jsonPath("$.password", is(expectedUser.getPassword()))
                );
    }

    @Test
    @DisplayName("getByLogin : Erreur interne.")
    void getUserByLoginRetourne500QuandErreurInterne() throws Exception {
        final String login = "toto";
        doThrow(new NullPointerException("Ça a pété quelque part!")).when(userService).getByLogin(login);

        final MvcResult mvcResult = mockMvc.perform(
                get("/user/" + login).accept(APPLICATION_JSON))
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
        final UserDto userToCreate = new UserDto();
        userToCreate.setLogin(login);

        doThrow(new UserAlreadyExistsException("login")).when(userService).create(any());

        final String userJson = objectMapper.writeValueAsString(userToCreate);

        final MvcResult mvcResult = mockMvc.perform(
                post("/user/").content(userJson).contentType(APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        assertEquals(0, mvcResult.getResponse().getContentLength());
    }

    @Test
    @DisplayName("create : L'utilisateur n'existe pas encore.")
    void createUserRetourne201QuandCreation() throws Exception {
        final String login = "toto";
        when(userService.getByLogin(login)).thenReturn(null);

        final UserDto userToCreate = new UserDto();
        userToCreate.setLogin(login);
        final String userJson = objectMapper.writeValueAsString(userToCreate);

        final UserDto newUser = new UserDto();
        newUser.setId(1L);
        newUser.setLogin(login);
        when(userService.create(argThat(sameLoginThan(newUser)))).thenReturn(newUser);

        mockMvc.perform(
                post("/user/").content(userJson).contentType(APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(newUser.getId().intValue())))
                .andExpect(jsonPath("$.login", is(newUser.getLogin()))).andReturn();
    }

    private ArgumentMatcher<UserDto> sameLoginThan(final UserDto newUser) {
        return userDto -> userDto.getLogin().equals(newUser.getLogin());
    }

}
