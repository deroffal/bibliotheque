package fr.deroffal.portail.api.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class AppLoggerFactory {

	Logger getLogger(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}

}
