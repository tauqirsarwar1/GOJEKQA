package com.gojek.stepdefs;

import com.gojek.lib.ScumberException;
import com.gojek.lib.WebDriverActions;
import com.gojek.pageobjects.ApplicationsPageObject;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

public class CommonSD {
    private final static Logger Log = Logger.getLogger(CommonSD.class.getName());
    private WebDriver itsDriver;
    private ApplicationsPageObject applicationsPageObject;


    public CommonSD() {
        Log.fine("Constructor: CommonSD");
    }

    @Before
    public void before(final Scenario scenario)
            throws ScumberException {
        itsDriver = WebDriverActions.openBrowser(scenario);
        applicationsPageObject = PageFactory.initElements(itsDriver, ApplicationsPageObject.class);

    }

    @Given("^User navigates to URL : \"([^\"]*)\" application$")
    public void userNavigatesToURLApplication(String appURL) {
        try {
            itsDriver.get(appURL);
        } catch (Exception e) {
            Log.info("Navigate to URL : " +appURL);
            throw (e);
        }

    }

    @When("the user moves to login page")
    public void theUserMovesToLoginPage() {
        try {
            applicationsPageObject.clickAccountList();
            applicationsPageObject.verifyloginPage();
        } catch (Exception e) {
            Log.info("Unable to navigate to login page");
            throw (e);
        }

    }

    @And("the user enters \"([^\"]*)\" in \"([^\"]*)\" field")
    public void theUserEntersInField(String fieldValue, String fieldName) {
        applicationsPageObject.typeTextInField(fieldName, fieldValue);
    }

    @And("clicks on the SignIn button")
    public void clicksOnTheSignInButton() {
        applicationsPageObject.clickLoginButton();

    }

    @Then("home page is displayed with \"([^\"]*)\" message")
    public void homePageIsDisplayedWithMessage(String arg0) {
        applicationsPageObject.verifyPageContents(arg0);
    }

    @When("the user logoff from the system")
    public void theUserLogoffFromTheSystem() {
        applicationsPageObject.logoff();
    }
}
