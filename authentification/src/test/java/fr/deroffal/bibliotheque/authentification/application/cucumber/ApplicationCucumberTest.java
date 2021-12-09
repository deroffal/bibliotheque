package fr.deroffal.bibliotheque.authentification.application.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    glue = { "fr.deroffal.bibliotheque.authentification" },
    features = { "src/test/resources/application/" },
    plugin = {"json:target/cucumber.json"}
)
public class ApplicationCucumberTest {

}
