package fr.deroffal.portail.authentification.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import fr.deroffal.portail.authentification.dto.UserDto;
import fr.deroffal.portail.authentification.entity.UserEntity;
import fr.deroffal.portail.authentification.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	private final ObjectMapper om = new ObjectMapper();

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	@DisplayName("getUserByLogin : L'utilisateur n'existe pas.")
	void getUserByLogin_retourne404_quandLoginInconnu() throws Exception {
		final String login = "toto";
		when(userService.getByLogin(login)).thenReturn(null);

		mockMvc.perform(get("/user/"+login))
				.andExpect(status().isNotFound());
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

		mockMvc.perform(get("/user/"+login))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(expectedUser.getId().intValue())))
				.andExpect(jsonPath("$.login", is(expectedUser.getLogin())))
				.andExpect(jsonPath("$.password", is(expectedUser.getPassword())));
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

		final MvcResult mvcResult = mockMvc.perform(
				post("/user/").content(userJson).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isConflict())
				.andReturn();

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

		mockMvc.perform(
				post("/user/").content(userJson).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(newUser.getId().intValue())))
				.andExpect(jsonPath("$.login", is(newUser.getLogin())))
				.andReturn();
	}

	private ArgumentMatcher<UserDto> sameLoginThan(final UserDto newUser) {
		return userDto -> userDto.getLogin().equals(newUser.getLogin());
	}

}
