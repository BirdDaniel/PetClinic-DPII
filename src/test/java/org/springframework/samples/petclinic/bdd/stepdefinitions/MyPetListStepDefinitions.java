//package org.springframework.samples.petclinic.bdd.stepdefinitions;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.UUID;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.test.annotation.DirtiesContext;
//
//import io.cucumber.java.en.Given;
//import io.cucumber.java.en.Then;
//import io.cucumber.java.en.When;
//import lombok.extern.java.Log;
//
//@Log
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@DirtiesContext
//public class MyPetListStepDefinitions extends AbstractStep{
//	
//	@LocalServerPort
//	private int port;
//	
//	@Given("I am logged in the system as {string}")
//	public void i_am_logged_in_the_system_as_owner(String username) {
//	    LoginOwnerStepDefinitions.loginAs(username, port, getDriver());
//	}
//	
//	@When("I press the button my pets")
//	public void IPressMyPets() throws Exception {		
//		getDriver().findElement(By.xpath("//div[@id='main-navbar']/ul/li[2]/a")).click();
//	    getDriver().findElement(By.linkText("My Pets")).click();
//	}
//	
//	@Then("the pet list appears")
//	public void the_pet_list_appears() {
//		assertEquals(getDriver().getCurrentUrl(), "http://localhost:" + port + "/owners/2/myPetList");
//		stopDriver();
//	}
//
//	
//	
//
//}
