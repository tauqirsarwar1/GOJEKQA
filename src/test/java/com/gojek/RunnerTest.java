package com.gojek;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        format = {"json:target/cucumber-reports/Cucumber.json",
                "junit:target/cucumber-reports/Cucumber.xml",
                "html:target/cucumber-reports"},
        features = {"src/test/resources/feature_files/SearchProducts.feature"},
        monochrome = true,
        tags = "@Regression")
public class RunnerTest {

}