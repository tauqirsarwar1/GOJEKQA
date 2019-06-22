package com.gojek.pageobjects;

import com.gojek.lib.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;

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

    private WebElement getFormField(String fieldName) {
        return itsDriver.findElement(By.name(fieldName));
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
        moveToElement(accountListLink);
        assertElementIsVisible(logoffButton, "element is not displayed");
        clickElement(logoffButton);
        assertElementIsVisible(loginButton, "login is not displayed");

    }

}