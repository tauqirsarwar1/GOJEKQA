package com.gojek.pageobjects;

import com.gojek.lib.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ApplicationsPageObject extends AbstractPage {
    private final WebDriver itsDriver;

    @Autowired
    public ApplicationsPageObject(final WebDriver driver) {
        super(driver);
        itsDriver = driver;
        PageFactory.initElements(driver, this);
    }


    @FindBy(id = "nav-link-accountList")
    private WebElement accountListLink;

    @FindBy(id = "signInSubmit")
    private WebElement loginButton;

    @FindBy(id = "nav-item-signout")
    private WebElement logoffButton;

    @FindBy(id = "nav-link-shopall")
    private WebElement shopAllLink;

    @FindBy(id = "comparison_add_to_cart_button-announce")
    private WebElement addToCart;

    @FindBy(id = "huc-v2-order-row-messages")
    private WebElement addedToCart;

    @FindBy(id = "twotabsearchtextbox")
    private WebElement searchBar;

    @FindBy(id = "nav-search-submit-text")
    private WebElement searchBarButton;

    @FindBy(xpath = "//select[@name='quantity']")
    private WebElement quantityItem;

    @FindBy(id = "nav-cart-count")
    private WebElement viewCart;

    @FindBy(name = "proceedToCheckout")
    private WebElement checkoutButton;

    @FindBy(xpath = "//h1")
    private WebElement shippingPage;

    private WebElement getFormField(String fieldName) {
        return itsDriver.findElement(By.name(fieldName));
    }

    private WebElement getOptionFromList(String fieldName) {
        return itsDriver.findElement(By.xpath("//a/span[contains(text(),'"+fieldName+"')]"));
    }

    private WebElement getOptionFromCatagory(String fieldName) {
        return itsDriver.findElement(By.xpath("//a/div/span[contains(text(),'"+fieldName+"')]"));
    }
    private List<WebElement> getItemFromcatagoryList(String item) {
        return itsDriver.findElements(By.xpath("//a/div/img[contains(@alt,'"+item+"')]"));
    }

    private List<WebElement> getDeleteItemFromCart(String item) {
        return itsDriver.findElements(By.xpath("//span/input[contains(@aria-label,'"+item+"')]"));
    }


    public void clickAccountList() {
        clickElement(accountListLink);
    }

    public void verifyloginPage() {
        assertElementIsVisible(loginButton, "login is not displayed");
    }


    public void typeTextInField(String fieldName, String fieldValue) {
        typeEditBox(getFormField(fieldName), fieldValue);
    }


    public void clickLoginButton() {
        clickElement(loginButton);
    }

    public void verifyPageContents(String text) {
        assertElementIsConstains(accountListLink, text);
    }

    public void logoff() {
        waitUntilElementIsClickable(accountListLink);
        moveToElement(accountListLink);
        waitUntilElementIsClickable(logoffButton);
        clickElement(logoffButton);
        assertElementIsVisible(loginButton, "login is not displayed");

    }

    public void clickListOption() {
        moveToElement(shopAllLink);
    }

    public void clickOptionFromShopAll(String option) {
        clickElement(getOptionFromList(option));
    }

    public void clickOptionFromCatagory(String option) {
        clickElement(getOptionFromCatagory(option));
    }

    public void clickOptionFromItemList(int number, String item) {
        moveToElement(getItemFromcatagoryList(item).get(number));
        clickElement(getItemFromcatagoryList(item).get(number));
    }

    public void clickAddToCartButton() {
        clickElement(addToCart);
    }

    public void verifyItemIntoCart() {
        assertElementIsVisible(addedToCart, "element is not displayed");
    }

    public void typeTextInSearchBar(String searchText) {
        typeEditBox(searchBar, searchText);
        searchBar.sendKeys(Keys.ENTER);
    }

    public void selectItemQuantityDelete(String num) {
        clickElement(viewCart);
        assertElementIsVisible(quantityItem, "not displayed");
        waitUntilElementIsClickable(quantityItem);
        selectListByVisibleText(quantityItem, num);
    }

    public void selectItemQuantity(String num) {
        assertElementIsVisible(quantityItem, "not displayed");
        waitUntilElementIsClickable(quantityItem);
        selectListByVisibleText(quantityItem, num);
    }

    public void clickViewCartButton() {
        clickElement(viewCart);
    }

    public void deleteItemFromCart(String item) {
        clickElement(getDeleteItemFromCart(item).get(0));
    }

    public void clickProceedToCartButton() {
        assertElementIsVisible(checkoutButton, "not displayed");
        clickElement(checkoutButton);
        assertElementIsVisible(shippingPage, "not displayed");
    }

    public void verifySearchResults(String item) {
        assertElementIsVisible(getItemFromcatagoryList(item).get(0), "not displayed");
    }
}
