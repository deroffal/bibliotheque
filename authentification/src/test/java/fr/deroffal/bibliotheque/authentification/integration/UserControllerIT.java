package fr.deroffal.bibliotheque.authentification.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/integration/clean.sql",
        "/integration/UserControllerIT/rechercherUtilisateurLoginConnu.sql" })
    @Test
    @DisplayName("Recherche d'un utilisateur : l'utilisateur est connu.")
    void rechercherUtilisateurLoginConnu() {
        final ResponseEntity<UserDto> response = restTemplate.getForEntity(buildUrl("/user/user2"), UserDto.class);

        assertThat(response.getStatusCode()).isEqualTo(OK);

        final UserDto user = response.getBody();

        assertThat(user).isNotNull();

        assertThat(user.id()).isEqualTo(3L);
        assertThat(user.username()).isEqualTo("user2");
        assertThat(user.password()).isEqualTo("$2a$10$IvID3zGmRTLpIB/uCnjxleEmk0hUe6Gyr9oKX6UqAZkWrb6xvrmvC");
    }

    @Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/integration/clean.sql",
        "/integration/UserControllerIT/rechercherUtilisateurLoginInconnu.sql" })
    @Test
    @DisplayName("Recherche d'un utilisateur : l'utilisateur est inconnu.")
    void rechercherUtilisateurLoginInconnu() {
        final ResponseEntity<String> response = restTemplate.getForEntity(buildUrl("/user/user3"), String.class);

        assertThat(response.getStatusCode()).isEqualTo(NOT_FOUND);

        final String message = response.getBody();

        assertThat(message).isEqualTo("Utilisateur user3 inconnu.");
    }

    private String buildUrl(final String uri) {
        return "http://localhost:" + port + uri;
    }
}
