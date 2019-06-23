package com.gojek.stepdefs;

import com.gojek.lib.ScumberException;
import com.gojek.lib.WebDriverActions;
import com.gojek.pageobjects.ApplicationsPageObject;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

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

    @When("the user selects shopall menu")
    public void theUserSelectsOption() {
        try {
            applicationsPageObject.clickListOption();
        } catch (Exception e) {
            Log.warning("Unable to click");
            throw (e);
        }

    }

    @And("clicks \"([^\"]*)\" option from shopall list")
    public void clicksOptionFromShopallList(String option) {
        try {
            applicationsPageObject.clickOptionFromShopAll(option);
        } catch (Exception e) {
            Log.warning("Unable to click");
            throw (e);
        }

    }


    @And("the user selects \"([^\"]*)\" from main category")
    public void theUserSelectsFromMainCategory(String option) {
        try {
            applicationsPageObject.clickOptionFromCatagory(option);
        } catch (Exception e) {
            Log.warning("Unable to click");
            throw (e);
        }

    }

    @And("selects (\\d+) product \"([^\"]*)\" from top in list")
    public void selectsHeadphoneItemFromTopInList(int index, String item) {
        applicationsPageObject.clickOptionFromItemList(index - 1, item);
    }

    @And("clicks add to cart button")
    public void clicksAddToCartButton() {
        applicationsPageObject.clickAddToCartButton();

    }

    @And("item is added successfully")
    public void itemIsAddedSuccessfully() {
        applicationsPageObject.verifyItemIntoCart();
    }

    @When("the user enters \"([^\"]*)\" in main search bar")
    public void theUserEntersInMainSearchBar(String searchText) {
        applicationsPageObject.typeTextInSearchBar(searchText);

    }

    @And("the user selects the quantity as \"([^\"]*)\" after delete")
    public void theUserSelectsQuantityDelete(String quantityItem) {
        applicationsPageObject.selectItemQuantityDelete(quantityItem);
    }

    @When("the user views the cart items")
    public void theUserViewsTheCartItems() {
        applicationsPageObject.clickViewCartButton();

    }

    @And("the user removes \"([^\"]*)\" item from cart")
    public void theUserRemovesItemFromCart(String deleteItem) {
        applicationsPageObject.deleteItemFromCart(deleteItem);
    }

    @And("Clicks on proceed to checkout button")
    public void clicksOnProceedToCheckoutButton() {
        applicationsPageObject.clickProceedToCartButton();
    }

    @And("the user selects quantity as \"([^\"]*)\"")
    public void theUserSelectsQuantityAs(String quantityItem) {
        applicationsPageObject.selectItemQuantity(quantityItem);
    }

    @Then("products \"([^\"]*)\" are visible on the searching page")
    public void productsAreVisibleOnTheSearchingPage(String item) {
        applicationsPageObject.verifySearchResults(item);

    }
}
