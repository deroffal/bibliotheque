package fr.deroffal.bibliotheque.authentification.adapter.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import fr.deroffal.bibliotheque.authentification.application.CreationUserService;
import fr.deroffal.bibliotheque.authentification.application.RecuperationUserService;
import fr.deroffal.bibliotheque.authentification.domain.model.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Api("/user")
@RestController
@RequestMapping("/public/user")
public class UserController {

    private final RecuperationUserService recuperationUserService;
    private final CreationUserService creationUserService;

    @ApiOperation(httpMethod = "GET", value = "Récupération d'un utilisateur")
    @GetMapping(value = "/{login}")
    @ResponseStatus(OK)
    public UserDto getByLogin(@PathVariable final String login) {
        return recuperationUserService.getByLogin(login);
    }

    @ApiOperation(httpMethod = "POST", value = "Création d'un utilisateur")
    @PostMapping(value = "/")
    @ResponseStatus(CREATED)
    public UserDto create(@RequestBody final UserDto userIn) {
        return creationUserService.create(userIn);
    }
}
