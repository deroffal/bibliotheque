package fr.deroffal.bibliotheque.livre.domain

import java.util.*

data class Livre(
    val id: UUID,
    val titre: String,
    val genre: String
)

class LivreNotFoundException : RuntimeException() {

}
