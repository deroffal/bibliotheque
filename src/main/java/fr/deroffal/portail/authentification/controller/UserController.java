package fr.deroffal.portail.authentification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.deroffal.portail.authentification.dto.UserDto;
import fr.deroffal.portail.authentification.entity.UserEntity;
import fr.deroffal.portail.authentification.exception.UserAlreadyExistsException;
import fr.deroffal.portail.authentification.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "/user")
@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(httpMethod = "GET", value = "Récupération d'un utilisateur")
	@GetMapping(value = "/{login}")
	public ResponseEntity<UserEntity> getUserByLogin(@PathVariable final String login) {
		final UserEntity user = userService.getByLogin(login);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@ApiOperation(httpMethod = "POST", value = "Création d'un utilisateur")
	@PostMapping(value = "/")
	public ResponseEntity<UserDto> createUser(@RequestBody final UserDto userIn) {
		final UserEntity user = userService.getByLogin(userIn.getLogin());
		if (user != null) {
			throw new UserAlreadyExistsException(userIn.getLogin());
		}
		return new ResponseEntity<>(userService.createUser(userIn), HttpStatus.CREATED);
	}

}
