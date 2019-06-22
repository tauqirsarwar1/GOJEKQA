package com.gojek.stepdefs;

import com.gojek.lib.Constants;
import com.gojek.lib.ScumberException;
import com.gojek.lib.WebDriverActions;
import com.gojek.pageobjects.ApplicationsPageObject;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Logger;

public class ProductSD {
    private final static Logger Log = Logger.getLogger(ProductSD.class.getName());
    private WebDriver itsDriver;
    private ApplicationsPageObject applicationsPageObject;

    public ProductSD() {
        Log.fine("Constructor: CommonSD");
    }

    @Before
    public void before(final Scenario scenario)
            throws ScumberException {
        itsDriver = WebDriverActions.openBrowser(scenario);
        applicationsPageObject = PageFactory.initElements(itsDriver, ApplicationsPageObject.class);

    }
}
