package fr.deroffal.portail.authentification.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import fr.deroffal.portail.AbstractControllerTest;
import fr.deroffal.portail.authentification.dto.UserDto;
import fr.deroffal.portail.authentification.entity.UserEntity;
import fr.deroffal.portail.authentification.exception.UserNotFoundException;
import fr.deroffal.portail.authentification.service.UserService;
import fr.deroffal.portail.exception.ExceptionMessage;

@WebMvcTest(UserController.class)
class UserControllerTest extends AbstractControllerTest {

	@MockBean
	private UserService userService;

	@Test
	@DisplayName("getUserByLogin : L'utilisateur n'existe pas.")
	void getUserByLogin_retourne404_quandLoginInconnu() throws Exception {
		final String login = "toto";
		doThrow(new UserNotFoundException(login)).when(userService).getByLogin(login);

		mockMvc.perform(get("/user/" + login)).andExpect(status().isNotFound());
	}

	@Test
	@DisplayName("getUserByLogin : L'utilisateur existe.")
	void getUserByLogin_retourne200EtUser_quandLoginConnu() throws Exception {
		final String login = "toto";
		final UserEntity expectedUser = new UserEntity();
		expectedUser.setId(35L);
		expectedUser.setLogin(login);
		expectedUser.setPassword("clm#^`|'!(,;''rt321201'");
		when(userService.getByLogin(login)).thenReturn(expectedUser);

		mockMvc.perform(get("/user/" + login)).andExpect(status().isOk()).andExpect(jsonPath("$.id", is(expectedUser.getId().intValue())))
				.andExpect(jsonPath("$.login", is(expectedUser.getLogin()))).andExpect(jsonPath("$.password", is(expectedUser.getPassword())));
	}

	@Test
	@DisplayName("getUserByLogin : Erreur interne.")
	void getUserByLogin_retourne500_quandErreurInterne() throws Exception {
		final String login = "toto";
		doThrow(new NullPointerException("Ça a pété quelque part!")).when(userService).getByLogin(login);

		final MvcResult mvcResult = mockMvc.perform(get("/user/" + login)).andExpect(status().isInternalServerError()).andReturn();

		ExceptionMessage em = om.readValue(mvcResult.getResponse().getContentAsString(), ExceptionMessage.class);
		assertEquals("/user/toto", em.getUri());
		final String message = em.getMessage();
		assertEquals("Une erreur interne est survenue :\nÇa a pété quelque part!", message);
	}

	@Test
	@DisplayName("createUser : L'utilisateur existe déjà.")
	void createUser_retourne409_quandDejaExistant() throws Exception {
		final String login = "toto";
		final UserEntity expectedUser = new UserEntity();
		when(userService.getByLogin(login)).thenReturn(expectedUser);

		final UserDto userToCreate = new UserDto();
		userToCreate.setLogin(login);
		final String userJson = om.writeValueAsString(userToCreate);

		final MvcResult mvcResult = mockMvc.perform(post("/user/").content(userJson).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isConflict()).andReturn();

		assertEquals(0, mvcResult.getResponse().getContentLength());
	}

	@Test
	@DisplayName("createUser : L'utilisateur n'existe pas encore.")
	void createUser_retourne201_quandCreation() throws Exception {
		final String login = "toto";
		when(userService.getByLogin(login)).thenReturn(null);

		final UserDto userToCreate = new UserDto();
		userToCreate.setLogin(login);
		final String userJson = om.writeValueAsString(userToCreate);

		final UserDto newUser = new UserDto();
		newUser.setId(1L);
		newUser.setLogin(login);
		when(userService.createUser(argThat(sameLoginThan(newUser)))).thenReturn(newUser);

		mockMvc.perform(post("/user/").content(userJson).contentType(APPLICATION_JSON_UTF8)).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(newUser.getId().intValue())))
				.andExpect(jsonPath("$.login", is(newUser.getLogin()))).andReturn();
	}

	private ArgumentMatcher<UserDto> sameLoginThan(final UserDto newUser) {
		return userDto -> userDto.getLogin().equals(newUser.getLogin());
	}

}
