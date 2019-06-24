package fr.deroffal.portail.webapp.login;

import com.stehno.ersatz.ErsatzServer;
import com.stehno.ersatz.junit5.ErsatzServerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.stehno.ersatz.ContentType.APPLICATION_JSON;
import static fr.deroffal.portail.webapp.TestUtils.readFile;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(ErsatzServerSupport.class)
class LoginServiceIT {

	private final ErsatzServer ersatzServer = new ErsatzServer();
	@Autowired
	private LoginService loginService;

	@Test
	@DisplayName("Recherche d'un utilisateur par son login")
	void loadUserByUsername() {
		ersatzServer.expectations(
				expectations -> expectations.get("/user/admin").called(1).responder(
						response -> response.code(SC_OK).body(readFile("/fr/deroffal/portail/webapp/login/userAdmin.json"), APPLICATION_JSON))
		).start();
		ReflectionTestUtils.setField(loginService, "authentificationUrl", ersatzServer.getHttpUrl());

		final UserDetails userDetails = loginService.loadUserByUsername("admin");

		assertEquals("admin", userDetails.getUsername());
		assertEquals("$2a$10$3AoDzKHV.ExSwFXq8SPjK.3qSozxVVngcB0Xd4iAQcVlvz4yBgh1e", userDetails.getPassword());
		assertEquals(List.of("ROLE_ADMIN", "ROLE_USER"), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).sorted().collect(Collectors.toList()));

		assertTrue(ersatzServer.verify());
	}

	@Test
	@DisplayName("Recherche d'un utilisateur par son login - pas d'utilisateur trouvé")
	void loadUserByUsername_notFound() {
		ersatzServer.expectations(
				expectations -> expectations.get("/user/admin").called(1).responder(
						response -> response.code(SC_NOT_FOUND))
		).start();
		ReflectionTestUtils.setField(loginService, "authentificationUrl", ersatzServer.getHttpUrl());

		assertThrows(UsernameNotFoundException.class, ()->loginService.loadUserByUsername("admin"));

		assertTrue(ersatzServer.verify());
	}

}
