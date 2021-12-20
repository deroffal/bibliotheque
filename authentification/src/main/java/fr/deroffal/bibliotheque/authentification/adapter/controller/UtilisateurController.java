package fr.deroffal.bibliotheque.authentification.adapter.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import fr.deroffal.bibliotheque.authentification.application.CreationUtilisateurService;
import fr.deroffal.bibliotheque.authentification.application.RecuperationUtilisateurService;
import fr.deroffal.bibliotheque.authentification.domain.model.Utilisateur;
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
@RequestMapping("/user")
public class UtilisateurController {

    private final RecuperationUtilisateurService recuperationUtilisateurService;
    private final CreationUtilisateurService creationUtilisateurService;

    @ApiOperation(httpMethod = "GET", value = "Récupération d'un utilisateur")
    @GetMapping(value = "/{login}")
    @ResponseStatus(OK)
    public Utilisateur getByLogin(@PathVariable final String login) {
        return recuperationUtilisateurService.getByLogin(login);
    }

    @ApiOperation(httpMethod = "POST", value = "Création d'un utilisateur")
    @PostMapping(value = "/")
    @ResponseStatus(CREATED)
    public Utilisateur create(@RequestBody final Utilisateur userIn) {
        return creationUtilisateurService.create(userIn);
    }
}
