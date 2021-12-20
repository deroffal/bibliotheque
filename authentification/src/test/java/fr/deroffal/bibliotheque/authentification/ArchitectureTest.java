package fr.deroffal.bibliotheque.authentification;

import static com.tngtech.archunit.base.DescribedPredicate.doNot;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;

@AnalyzeClasses(packages = { "fr.deroffal.bibliotheque.authentification", "javax.persistence" })
public class ArchitectureTest {

    private static final String BASE_PACKAGE = "fr.deroffal.bibliotheque.authentification";

    private static final DescribedPredicate<JavaClass> resideInMyApp = resideInAPackage(BASE_PACKAGE + "..");

    @ArchTest
    public static final ArchRule repositoryAdapter_shouldNot_beAccessed = ArchRuleDefinition.classes()
        .that().resideInAPackage("fr.deroffal.bibliotheque.authentification.adapter.repository..")
        .should().onlyBeAccessed().byClassesThat(resideInAnyPackage("fr.deroffal.bibliotheque.authentification.adapter.repository.."));

    @ArchTest
    public static final ArchRule only_repositoryAdapter_shouldAccess_javaxPersistenceClasses = ArchRuleDefinition.noClasses()
        .that(resideInMyApp).and(doNot(resideInAPackage("fr.deroffal.bibliotheque.authentification.adapter.repository")))
        .should().dependOnClassesThat().resideInAPackage("javax.persistence..");
}
