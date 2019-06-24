package fr.deroffal.portail.api.authentification.integration;

import fr.deroffal.portail.api.authentification.role.RoleEntity;
import fr.deroffal.portail.api.authentification.utilisateur.UserEntity;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;

@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIT extends DBTestCase {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	public UserIT() {
		super();
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:devDb");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
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
		return new FlatXmlDataSetBuilder().build(this.getClass().getResourceAsStream("/dataset/authentification.xml"));
	}

	private String buildUrl(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	@DisplayName("Recherche d'un utilisateur : l'utilisateur est connu.")
	void rechercherUtilisateurLoginConnu() {
		final ResponseEntity<UserEntity> response = restTemplate.getForEntity(buildUrl("/user/user2"), UserEntity.class);
		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		final UserEntity actualUser = response.getBody();

		assertEquals(3L, actualUser.getId().longValue());
		assertEquals("user2", actualUser.getLogin());
		assertEquals("$2a$10$IvID3zGmRTLpIB/uCnjxleEmk0hUe6Gyr9oKX6UqAZkWrb6xvrmvC", actualUser.getPassword());

		final Collection<RoleEntity> actualUserRoles = actualUser.getRoles();
		assertAll("Vérififcation des rôles",
				  () -> assertEquals(1, actualUserRoles.size()),
				  () -> assertEquals("USER", actualUserRoles.iterator().next().getRole())
		);
	}

	@Test
	@DisplayName("Recherche d'un utilisateur : l'utilisateur est inconnu.")
	void rechercherUtilisateurLoginInconnu() {
		final ResponseEntity<?> response = restTemplate.getForEntity(buildUrl("/user/user3"), Object.class);
		assertNotNull(response);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		//L'objet reçu correspond à un ExceptionMessage.
		final Object body = response.getBody();
		final Map<String, Object> messages = (Map<String, Object>) body;
		assertEquals("/user/user3", messages.get("uri"));
		assertEquals("Utilisateur non-existant : user3", messages.get("message"));
	}


}
