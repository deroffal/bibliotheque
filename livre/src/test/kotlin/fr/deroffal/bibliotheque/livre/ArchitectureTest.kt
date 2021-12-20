package fr.deroffal.bibliotheque.livre

import com.tngtech.archunit.base.DescribedPredicate
import com.tngtech.archunit.base.DescribedPredicate.doNot
import com.tngtech.archunit.core.domain.JavaClass
import com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage
import com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage
import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.junit.ArchTests
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import com.tngtech.archunit.lang.syntax.elements.GivenClassesConjunction
import fr.deroffal.bibliotheque.livre.architecture.ReglesArchitectureAdapter
import fr.deroffal.bibliotheque.livre.architecture.ReglesArchitectureDomaine


val appBasePackage: String = ArchitectureTest::class.java.`package`.name
val resideInMyApp: DescribedPredicate<JavaClass> = resideInAPackage("$appBasePackage..")
val noInternalClasses: GivenClassesConjunction = noClasses().that(resideInMyApp)
fun noInternalClassesOutsideOf(vararg packages: String): GivenClassesConjunction = noInternalClasses.and(doNot(resideInAnyPackage(*packages)))

@AnalyzeClasses(
    packagesOf = [ArchitectureTest::class],
    packages = [
        "fr.deroffal.bibliotheque",
        "org.springframework.security", "org.springframework.web", "org.springframework.http",
        "springfox.documentation",
        "javax.persistence", "org.springframework.data"
    ]
)
class ArchitectureTest {

    @ArchTest
    val domainRules = ArchTests.`in`(ReglesArchitectureDomaine::class.java)

    @ArchTest
    val adapterRules = ArchTests.`in`(ReglesArchitectureAdapter::class.java)

}

