package fr.deroffal.bibliotheque.api.authentification.utilisateur;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Api("/user")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(httpMethod = "GET", value = "Récupération d'un utilisateur")
	@GetMapping(value = "/{login}")
	public ResponseEntity<UserDto> getByLogin(@PathVariable final String login) {
		final UserDto user = userService.getByLogin(login);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@ApiOperation(httpMethod = "POST", value = "Création d'un utilisateur")
	@PostMapping(value = "/")
	public ResponseEntity<UserDto> create(@RequestBody final UserDto userIn) {
		final UserDto user = userService.create(userIn);
		return new ResponseEntity<>(user, CREATED);
	}

}
