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

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.deroffal.bibliotheque.authentification.application.CreationUtilisateurService;
import fr.deroffal.bibliotheque.authentification.application.RecuperationUtilisateurService;
import fr.deroffal.bibliotheque.authentification.application.UserAlreadyExistsException;
import fr.deroffal.bibliotheque.authentification.application.UserNotFoundException;
import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest
class UtilisateurControllerTest {

    @Autowired
    private MockMvc mockMvc ;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecuperationUtilisateurService recuperationUtilisateurService;

    @MockBean
    private CreationUtilisateurService creationUtilisateurService;

    @Test
    @DisplayName("getByLogin : L'utilisateur n'existe pas.")
    void getUserByLoginRetourne404QuandLoginInconnu() throws Exception {
        final String login = "toto";
        doThrow(new UserNotFoundException(login)).when(recuperationUtilisateurService).getByLogin(login);

        mockMvc.perform(get("/user/" + login))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("getByLogin : L'utilisateur existe.")
    void getUserByLoginRetourne200EtUserQuandLoginConnu() throws Exception {
        final String login = "toto";
        final Utilisateur expectedUser = new Utilisateur(35L, login, "clm#^`|'!(,;''rt321201'", null);
        when(recuperationUtilisateurService.getByLogin(login)).thenReturn(expectedUser);

        mockMvc.perform(get("/user/" + login))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(expectedUser.id().intValue())))
            .andExpect(jsonPath("$.username", is(expectedUser.username())))
            .andExpect(jsonPath("$.password", is(expectedUser.password())))
        ;
    }

    @Test
    @DisplayName("getByLogin : Erreur interne.")
    void getUserByLoginRetourne500QuandErreurInterne() throws Exception {
        final String login = "toto";
        doThrow(new NullPointerException("Ça a pété quelque part!")).when(recuperationUtilisateurService).getByLogin(login);

        final MvcResult mvcResult = mockMvc.perform(get("/user/" + login).accept(APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andReturn();

        final String erreur =mvcResult.getResponse().getContentAsString(UTF_8);
        assertEquals("Ça a pété quelque part!", erreur);
    }

    @Test
    @DisplayName("create : L'utilisateur existe déjà.")
    void createUserRetourne409QuandDejaExistant() throws Exception {
        final String login = "toto";
        final Utilisateur userToCreate = new Utilisateur(null, login, null, null);

        doThrow(new UserAlreadyExistsException("username")).when(creationUtilisateurService).create(any());

        final String userJson = objectMapper.writeValueAsString(userToCreate);

        mockMvc.perform(post("/user/").content(userJson).contentType(APPLICATION_JSON))
            .andExpect(status().isConflict());

    }

    @Test
    @DisplayName("create : L'utilisateur n'existe pas encore.")
    void createUserRetourne201QuandCreation() throws Exception {
        final String login = "toto";
        when(recuperationUtilisateurService.getByLogin(login)).thenReturn(null);

        final Utilisateur userToCreate = new Utilisateur(null, login, null, null);
        final String userJson = objectMapper.writeValueAsString(userToCreate);

        final Utilisateur newUser = new Utilisateur(1L, login, null, null);
        when(creationUtilisateurService.create(argThat(sameLoginThan(newUser)))).thenReturn(newUser);

        mockMvc.perform(post("/user/").content(userJson).contentType(APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(newUser.id().intValue())))
            .andExpect(jsonPath("$.username", is(newUser.username())))
            .andReturn();
    }

    private static ArgumentMatcher<Utilisateur> sameLoginThan(final Utilisateur newUser) {
        return userDto -> userDto.username().equals(newUser.username());
    }
}
