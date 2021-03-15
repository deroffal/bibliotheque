package fr.deroffal.bibliotheque.webapp.login;

import fr.deroffal.bibliotheque.webapp.security.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

	@InjectMocks
	private LoginController loginController;

	@Mock
	private SecurityService securityService;

	private static Stream<Arguments> loginRedirectionArgsProvider() {
		return Stream.of(
				arguments(true, "index.html"),
				arguments(false, "login.html")
		);
	}

	@MethodSource("loginRedirectionArgsProvider")
	@ParameterizedTest(name = "userLoggedIn ''{0}'' redirects to {1}")
	void loginRedirection(final boolean userLoggedIn,final String expectedRedirection) {
		when(securityService.isUserLoggedIn()).thenReturn(userLoggedIn);

		assertEquals(expectedRedirection, loginController.login());
	}

	@Test
	void index(){
		assertEquals("index.html", loginController.index());
	}

}
