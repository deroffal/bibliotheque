package fr.deroffal.bibliotheque.livre

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition
import com.tngtech.archunit.library.Architectures

@AnalyzeClasses(
    packagesOf = [ArchitectureTest::class],
    packages = ["fr.deroffal.bibliotheque", "springfox.documentation", "org.springframework.security"]
)
class ArchitectureTest {

    @ArchTest
    val `Valider l'architecture hexagonale` = ArchRuleDefinition.classes().that().resideInAPackage("$APP_DOMAIN_BASE_PACKAGE..")
        .should().onlyAccessClassesThat().resideInAnyPackage(
            "$APP_DOMAIN_BASE_PACKAGE..",
            "java.util..",
            "java.time..",
            "java.lang..",
            "kotlin.jvm.."
        )

    @ArchTest
    val `Isolation des dependances Swagger dans l'adapter apidocs` = Architectures.layeredArchitecture()
        .layer("swagger").definedBy("springfox.documentation..")
        .layer("apiDocsAdapter").definedBy("$APP_ADAPTERS_BASE_PACKAGE.apidocs")
        .whereLayer("swagger").mayOnlyBeAccessedByLayers("apiDocsAdapter")

    @ArchTest
    val `Isolation des dependances Spring Security & Bibliotheque Seurity-Auth dans le controller authentification` =
        Architectures.layeredArchitecture()
            .layer("springSecurity").definedBy("org.springframework.security..")
            .layer("bibliothequeAuthSecurityApi").definedBy("fr.deroffal.bibliotheque.securite.auth..")
            .layer("authentificationController").definedBy("$APP_ADAPTERS_BASE_PACKAGE.controller.authentification")
            .whereLayer("bibliothequeAuthSecurityApi").mayOnlyBeAccessedByLayers("authentificationController")

    @ArchTest
    val `Isolation des dependances Spring Security & Bibliotheque Seurity dans l'adapter authentification` =
        Architectures.layeredArchitecture()
            .layer("springSecurity").definedBy("org.springframework.security..")
            .layer("bibliothequeSecuriteApi").definedBy("fr.deroffal.bibliotheque.securite..")
            .layer("authentificationAdapter").definedBy("$APP_ADAPTERS_BASE_PACKAGE.authentification")
            .whereLayer("springSecurity").mayOnlyBeAccessedByLayers("authentificationAdapter", "bibliothequeSecuriteApi")

    @ArchTest
    val `Isolation des dependances Bibliotheque Security - details dans l'adapter authentification` = Architectures.layeredArchitecture()
        .layer("bibliothequeDetailsSecurityApi").definedBy("fr.deroffal.bibliotheque.securite.details..")
        .layer("authentificationAdapter").definedBy("$APP_ADAPTERS_BASE_PACKAGE.authentification")
        .whereLayer("bibliothequeDetailsSecurityApi").mayOnlyBeAccessedByLayers("authentificationAdapter")


    companion object {
        private val APP_BASE_PACKAGE = ArchitectureTest::class.java.`package`.name
        private val APP_ADAPTERS_BASE_PACKAGE = "$APP_BASE_PACKAGE.adapter"
        private val APP_DOMAIN_BASE_PACKAGE = "$APP_BASE_PACKAGE.domain"
    }
}
