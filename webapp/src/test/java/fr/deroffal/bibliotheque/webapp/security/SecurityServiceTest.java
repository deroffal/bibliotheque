package fr.deroffal.bibliotheque.webapp.security;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SecurityServiceTest {

	private final SecurityService securityService = new SecurityService();

	@AfterAll
	static void afterAll(){
		SecurityContextHolder.clearContext();
	}

	private static Stream<Arguments> isUserLoggedInArgsProvider() {
		return Stream.of(
				arguments(mock(AnonymousAuthenticationToken.class), false),
				arguments(mock(UsernamePasswordAuthenticationToken.class), true)
		);
	}

	@MethodSource("isUserLoggedInArgsProvider")
	@ParameterizedTest(name = "userLoggedIn retourne ''{1}'' si l'authentification est {0}")
	void isUserLoggedIn(final Authentication authentication, final boolean expected){
		final SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);

		when(securityContext.getAuthentication()).thenReturn(authentication);

		assertEquals(expected, securityService.isUserLoggedIn());
	}


}