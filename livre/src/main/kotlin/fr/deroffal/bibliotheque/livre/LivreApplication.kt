package fr.deroffal.bibliotheque.livre

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["fr.deroffal.bibliotheque"])
class LivreApplication

fun main(args: Array<String>) {
    runApplication<LivreApplication>(*args)
}

