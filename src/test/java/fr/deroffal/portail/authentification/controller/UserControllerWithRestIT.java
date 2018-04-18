package fr.deroffal.portail.authentification.controller;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import fr.deroffal.portail.PortailConfigutation;
import fr.deroffal.portail.authentification.entity.UserEntity;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext//FIXME on ne va quand même pas recharger le contexte à chaque fois!
class UserControllerWithRestIT extends DBTestCase {

	private static final String URL = "http://localhost:";

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;


	UserControllerWithRestIT() {
		super();
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.h2.Driver");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:h2:mem:portailtest");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
		System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, PortailConfigutation.SCHEMA_AUTHENTIFICATION);
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
		final FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		return builder.build(this.getClass().getResourceAsStream("/dataset/authentification.xml"));
	}

	@Test
	@DisplayName("Integration avec appel REST")
	void avec_appel_rest() {
		ResponseEntity<UserEntity> response = restTemplate.getForEntity(buildUrl("/user/user2"), UserEntity.class);
		assertNotNull(response);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		final UserEntity actualUser = response.getBody();

		assertEquals(3L, actualUser.getId().longValue());
		assertEquals("user2", actualUser.getLogin());
		assertEquals("$2a$10$IvID3zGmRTLpIB/uCnjxleEmk0hUe6Gyr9oKX6UqAZkWrb6xvrmvC", actualUser.getPassword());
	}

	private String buildUrl(String uri) {
		return URL + port + uri;
	}

}
