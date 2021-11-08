package fr.deroffal.bibliotheque.authentification.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD;
import static org.dbunit.PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME;

import java.util.Collection;
import java.util.Map;

import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

@Disabled
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIT extends DBTestCase {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    public UserIT() {
        super();
        System.setProperty(DBUNIT_DRIVER_CLASS, "org.testcontainers.jdbc.ContainerDatabaseDriver");
        System.setProperty(DBUNIT_CONNECTION_URL, "jdbc:tc:postgresql:13.2:///devDb");
        System.setProperty(DBUNIT_USERNAME, "test");
        System.setProperty(DBUNIT_PASSWORD, "test");
    }

    @BeforeEach
    void beforeEach() throws Exception {
        super.setUp();
    }

    @AfterEach
    void afterEach() throws Exception {
        super.tearDown();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(getClass().getResourceAsStream("/dataset/authentification.xml"));
    }

    private String buildUrl(final String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    @DisplayName("Recherche d'un utilisateur : l'utilisateur est connu.")
    void rechercherUtilisateurLoginConnu() {
        final HttpEntity<Object> httpEntity = getObjectHttpEntity();
        final ResponseEntity<UserDto> response = restTemplate.exchange(buildUrl("/user/user2"), HttpMethod.GET, httpEntity, UserDto.class);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        final UserDto actualUser = response.getBody();

        assertEquals(3L, actualUser.id().longValue());
        assertEquals("user2", actualUser.username());
        assertEquals("$2a$10$IvID3zGmRTLpIB/uCnjxleEmk0hUe6Gyr9oKX6UqAZkWrb6xvrmvC", actualUser.password());

        final Collection<String> actualUserRoles = actualUser.roles();
        assertThat(actualUserRoles).containsExactly("USER");
    }

    @Test
    @DisplayName("Recherche d'un utilisateur : l'utilisateur est inconnu.")
    void rechercherUtilisateurLoginInconnu() {
        final HttpEntity<Object> httpEntity = getObjectHttpEntity();
        final ResponseEntity<?> response = restTemplate.exchange(buildUrl("/user/user3"), HttpMethod.GET, httpEntity, Object.class);

        assertNotNull(response);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        //L'objet reçu correspond à un ExceptionMessage.
        final Object body = response.getBody();
        final Map<String, Object> messages = (Map<String, Object>) body;
        assertEquals("/user/user3", messages.get("uri"));
        assertEquals("Utilisateur non-existant : user3", messages.get("message"));
    }

    //Génère un header pour admin/admin
    private HttpEntity<Object> getObjectHttpEntity() {
        final HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(headers);
    }
}
