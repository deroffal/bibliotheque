package fr.deroffal.portail.controller;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/public")
@RestController
@RequestMapping(value = "/public")
public class PingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PingController.class);

	@ApiOperation(httpMethod = "GET", value = "Un ping du serveur")
	@GetMapping(value = "/ping")
	public ResponseEntity<String> ping() {
		LOGGER.info("OK !");
		return new ResponseEntity<>("Pong", HttpStatus.OK);
	}

	/**
	 * Temporaire : génération de la dtd.
	 */
	@GetMapping(value = "/dtd")
	public ResponseEntity<String> dtd() throws Exception {
		// database connection
		Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:mem:portail", "sa", "");
		IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

		// write DTD file
		FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("database.dtd"));
		return new ResponseEntity<>("dtd générée!", HttpStatus.OK);
	}

}
