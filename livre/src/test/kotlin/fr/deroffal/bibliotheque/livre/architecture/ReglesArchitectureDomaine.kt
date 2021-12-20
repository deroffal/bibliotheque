package fr.deroffal.bibliotheque.livre.architecture

import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import fr.deroffal.bibliotheque.livre.appBasePackage

class ReglesArchitectureDomaine {

    private val domainBasePackage = "$appBasePackage.domain"

    @ArchTest
    val `Valider l'architecture hexagonale` = ArchRuleDefinition.classes()
        .that().resideInAPackage("$domainBasePackage..")
        .should().onlyAccessClassesThat().resideInAnyPackage(
            "$domainBasePackage..",
            "java.util..",
            "java.time..",
            "java.lang..",
            "kotlin.jvm.."
        )
}
