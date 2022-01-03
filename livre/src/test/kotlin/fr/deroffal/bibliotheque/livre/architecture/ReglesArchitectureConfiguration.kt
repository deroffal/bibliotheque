package fr.deroffal.bibliotheque.livre.architecture

import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.library.Architectures
import fr.deroffal.bibliotheque.livre.appBasePackage
import fr.deroffal.bibliotheque.livre.noInternalClassesOutsideOf

class ReglesArchitectureConfiguration {

    private val configurationBasePackage = "$appBasePackage.configuration"

    @ArchTest
    val `Isolation des dependances de la configuration Swagger dans la configuration` = noInternalClassesOutsideOf(configurationBasePackage)
        .should().dependOnClassesThat().resideInAPackage("springfox.documentation..")
}
