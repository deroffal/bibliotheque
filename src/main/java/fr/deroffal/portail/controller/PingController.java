package fr.deroffal.portail.controller;

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

	@ApiOperation(httpMethod = "GET", value = "Un ping du serveur")
	@GetMapping(value = "/ping")
	public ResponseEntity<String> ping() {
		return new ResponseEntity<>("Pong", HttpStatus.OK);
	}

}
