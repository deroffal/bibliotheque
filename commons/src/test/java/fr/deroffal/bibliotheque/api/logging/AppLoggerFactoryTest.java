package fr.deroffal.bibliotheque.api.logging;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppLoggerFactoryTest {

	private AppLoggerFactory appLoggerFactory = new AppLoggerFactory();

	@Test
	@DisplayName("La factory appelle la factory de logger")
	void getLogger() {
		assertNotNull(appLoggerFactory.getLogger(Long.class));
	}

}