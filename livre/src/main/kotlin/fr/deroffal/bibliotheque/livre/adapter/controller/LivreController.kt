package fr.deroffal.bibliotheque.livre.adapter.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LivreController {

    @GetMapping
    fun get(uuid: String)=null
}
