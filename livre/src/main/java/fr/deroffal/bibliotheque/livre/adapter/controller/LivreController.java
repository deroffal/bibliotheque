package fr.deroffal.bibliotheque.livre.adapter.controller;

import fr.deroffal.bibliotheque.livre.domain.model.Livre;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LivreController {

    @GetMapping
    public Livre get(final String uuid) {
        return null;
    }
}
