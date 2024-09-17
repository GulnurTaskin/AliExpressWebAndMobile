package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-reports"},
        features = "src/test/resources/features",
        glue = "stepDefinitions",

        tags = "@web",

        dryRun = false // true oldugunda sadece eksik adim var mi diye kontrol eder
)
public class TestRunner {

}