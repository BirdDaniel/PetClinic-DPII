package org.springframework.samples.petclinic.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@RunWith(Cucumber.class)
@CucumberOptions(
        features= {"src/test/java/"},	
        tags = {"not @ignore"},
        plugin = {"pretty",                                
                "json:target/cucumber-reports/cucumber-report.json"}, 
        monochrome=true)
public class CucumberUITest {
	  }

