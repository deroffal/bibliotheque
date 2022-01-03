package fr.deroffal.bibliotheque.livre.architecture

import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition
import fr.deroffal.bibliotheque.livre.appBasePackage
import fr.deroffal.bibliotheque.livre.noInternalClassesOutsideOf

class ReglesArchitectureAdapter {

    private val adapterBasePackage = "$appBasePackage.adapter"

    @ArchTest
    val `Independance des adapters` = SlicesRuleDefinition.slices()
        .matching("$appBasePackage.(adapter).(*)").namingSlices("$1 '$2'")
        .should().notDependOnEachOther()

    @ArchTest
    val `Isolation des dependances Spring Web dans les adapters` = noInternalClassesOutsideOf("$adapterBasePackage..")
        .should().dependOnClassesThat().resideInAnyPackage("org.springframework.web..", "org.springframework.http..")

    @ArchTest
    val `Isolation des dependances Spring Security & Bibliotheque-Securite dans le controller authentification` =
        noInternalClassesOutsideOf("$adapterBasePackage.controller.authentification", "$adapterBasePackage.authentification")
            .should().dependOnClassesThat().resideInAnyPackage("org.springframework.security..", "fr.deroffal.bibliotheque.securite..")

    @ArchTest
    val `Isolation des dependances aux bases de donnees dans l'adapter repository` = noInternalClassesOutsideOf("$adapterBasePackage.repository")
        .should().dependOnClassesThat().resideInAnyPackage("javax.persistence..", "org.springframework.data..", "org.hibernate..")

}
