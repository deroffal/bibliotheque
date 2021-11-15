package fr.deroffal.bibliotheque.livre;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = { "fr.deroffal.bibliotheque", "springfox.documentation", "org.springframework.security" })
class ArchitectureTest {

    private static final String APP_BASE_PACKAGE = "fr.deroffal.bibliotheque.livre";
    private static final String APP_ADAPTERS_BASE_PACKAGE = APP_BASE_PACKAGE + ".adapter";
    private static final String APP_DOMAIN_BASE_PACKAGE = APP_BASE_PACKAGE + ".domain";

    //@formatter:off
    @ArchTest
    static final ArchRule swaggerDependencies_shouldResideIn_apidocsAdapter = layeredArchitecture()
                .layer("swagger").definedBy("springfox.documentation..")
                .layer("apiDocsAdapter").definedBy(APP_ADAPTERS_BASE_PACKAGE+".apidocs")
            .whereLayer("swagger").mayOnlyBeAccessedByLayers("apiDocsAdapter");

    @ArchTest
    static final ArchRule securitiesAuthDependencies_shouldResideIn_authentificationControllerAdapter = layeredArchitecture()
            .layer("springSecurity").definedBy( "org.springframework.security..")
            .layer("bibliothequeAuthSecurityApi").definedBy( "fr.deroffal.bibliotheque.securite.auth..")
            .layer("authentificationController").definedBy( APP_ADAPTERS_BASE_PACKAGE+".controller.authentification")
        .whereLayer("bibliothequeAuthSecurityApi").mayOnlyBeAccessedByLayers( "authentificationController");

    @ArchTest
    static final ArchRule springSecurityDependencies_shouldResideIn_authentificationAdapter = layeredArchitecture()
        .layer("springSecurity").definedBy( "org.springframework.security..")
        .layer("bibliothequeSecuriteApi").definedBy( "fr.deroffal.bibliotheque.securite..")
        .layer("authentificationAdapter").definedBy(APP_ADAPTERS_BASE_PACKAGE+".authentification")
        .whereLayer("springSecurity").mayOnlyBeAccessedByLayers("authentificationAdapter", "bibliothequeSecuriteApi");

    @ArchTest
    static final ArchRule bibliothequeSecurityDetailsDependencies_shouldResideIn_authentificationAdapter = layeredArchitecture()
        .layer("bibliothequeDetailsSecurityApi").definedBy( "fr.deroffal.bibliotheque.securite.details..")
        .layer("authentificationAdapter").definedBy(APP_ADAPTERS_BASE_PACKAGE+".authentification")
            .whereLayer("bibliothequeDetailsSecurityApi").mayOnlyBeAccessedByLayers("authentificationAdapter");

    @ArchTest
    static final ArchRule enforceHexagonalArchitechture = ArchRuleDefinition.classes().that().resideInAPackage(APP_DOMAIN_BASE_PACKAGE+"..")
    .should().onlyAccessClassesThat().resideInAnyPackage(
            APP_DOMAIN_BASE_PACKAGE+"..",
        "java.util..",
        "java.time..",
        "java.lang..",
        "kotlin.jvm.."
    );
    //@formatter:on
}
