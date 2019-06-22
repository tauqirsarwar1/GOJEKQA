package com.gojek.lib;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public abstract class AbstractPage implements ScreenDriver<WebDriver> {
    private final static Logger Log = Logger.getLogger(AbstractPage.class.getName());

    private final WebDriver itsDriver;
    private final WebDriverWait itsWait;
    private final WebDriverWait itsNoWait;

    public AbstractPage(final WebDriver driver) {
        itsDriver = driver;
        PageFactory.initElements(driver, this);
        itsWait = createWebWaitDriver(Constants.WAIT_TIMEOUT_DEFAULT);
        itsNoWait = createWebWaitDriver(Constants.WAIT_TIMEOUT_NOWAIT);
    }


    @Override
    public WebDriver getDriver() {
        return itsDriver;
    }

    protected void moveToElement(final String linkText) {
        final WebElement we = itsWait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
        final String message = String.format("FAIL : Element with linktext '%s'", linkText);

        assertCheckIfNotNull(message, we);
        moveToElement(we);
    }


    protected void moveToElement(final WebElement we) {
        final String message = String.format("FAIL : Element with these details is NULL : '%s'", we);
        final Actions actions = new Actions(itsDriver);

        assertCheckIfNotNull(message, we);
        actions.moveToElement(we).build().perform();
    }


    protected void typeEditBox(final WebElement editBox, final String valueToType) {
        if (waitUntilElementIsVisible(editBox) != null) {
            editBox.clear();
            editBox.sendKeys(valueToType);
        }
    }


    protected WebDriver getHandleToWindow(final String title) {
        WebDriver popup = null;

        final Set<String> windowIterator = itsDriver.getWindowHandles();

        Log.info("No of windows: " + windowIterator.size());

        for (final String s : windowIterator) {
            final String windowHandle = s;

            popup = itsDriver.switchTo().window(windowHandle);

            Log.info("Window Title: " + popup.getTitle() + " URL: " + popup.getCurrentUrl());

            if (popup.getTitle().contains(title)) {
                Log.info("Selected Window Title : " + popup.getTitle());
                return popup;
            }
        }

        final String popupTitle = popup != null ? popup.getTitle() : "<<Missing_Popup>>";

        Log.info("Window Title: " + popupTitle);

        return popup;
    }


    protected WebElement findParentElement(final WebElement element, final int level) {
        WebElement localElement = element;

        for (int i = 0; i < level; i++) {
            localElement = localElement.findElement(By.xpath(".."));
        }
        return localElement;
    }

    protected void clickOnElementAfterReplaceValueFromXpath(
            final String xpath,
            final String target,
            final String relacement) {
        clickOnElementFromXpath(xpath.replace(target, relacement));
    }

    protected void checkTextToBePresentInElement(
            final WebElement element,
            final String valueToCheck,
            final String message) {
        final String mesageIfNull = String.format("FAIL : Element with these details is NULL '%s'", element);
        assertCheckIfNotNull(mesageIfNull, element);
        assertCheckIfTrue(message, itsWait.until(ExpectedConditions.textToBePresentInElement(element,
                valueToCheck)).booleanValue());
    }

    /**
     * This method will check if Text is present in the element
     *
     * @param element      - web element
     * @param valueToCheck - text to check
     */
    protected boolean checkTextToBePresentInElement(final WebElement element, final String valueToCheck) {
        return itsWait.until(ExpectedConditions.textToBePresentInElement(element, valueToCheck)).booleanValue();
    }

    /**
     * This method will assert/check for true output of a condition
     *
     * @param message          - message when the output of condition is false
     * @param conditionToCheck - condition to evaluate
     */
    protected void assertCheckIfTrue(final String message, final boolean conditionToCheck) {
        Assert.assertTrue(message, conditionToCheck);
    }

    /**
     * This method will assert/check for false output of a condition
     *
     * @param message          - message when the output of condition is true
     * @param conditionToCheck - condition to evaluate
     */
    protected void assertCheckIfNotNull(final String message, final boolean conditionToCheck) {
        Assert.assertNotNull(message, Boolean.valueOf(conditionToCheck));
    }

    /**
     * This method will assert/check for false output of a condition
     *
     * @param message          - message when the output of condition is true
     * @param conditionToCheck - condition to evaluate
     */
    protected void assertCheckIfFalse(final String message, final boolean conditionToCheck) {
        Assert.assertFalse(message, conditionToCheck);
    }

    /**
     * This method will go through all the texts of the elements to check is a text is present
     *
     * @param listOfElements - list of Elements
     * @param textToCheck    - value that needs to be replaced from xpath
     */
    protected void checkIfTextOfAnElementIsPresentInAList(
            final List<WebElement> listOfElements,
            final String textToCheck) {
        final HashSet<String> textFromElements = new HashSet<String>();

        for (final WebElement element : listOfElements) {
            textFromElements.add(element.getText().trim());
        }

        assertCheckIfTrue(String.format("FAIL: value '%s' is not present in the list : %s ", textToCheck,
                textFromElements), textFromElements.contains(textToCheck));
    }

    /**
     * This method will click on the element when the given text matches
     *
     * @param listOfElements - list of Elements
     * @param textToCheck    - value that needs to be replaced from xpath
     */
    protected void clickOnElementWhenTextMatches(final List<WebElement> listOfElements, final String textToCheck) {
        boolean found = false;

        for (final WebElement element : listOfElements) {
            if (element.getText().trim().contains(textToCheck)) {
                clickElement(element);
                found = true;
                break;
            }
        }

        if (found == false) {
            Assert.fail(String.format("FAIL : '%s' Button is not present ", textToCheck));
        }
    }

    /**
     * This method will wait till element is Clickable
     *
     * @param element - webelement
     */
    protected WebElement waitUntilElementIsClickable(final WebElement element) {
        final String message = String.format("FAIL : Element with these details is NULL : '%s'", element);
        assertCheckIfNotNull(message, element);
        return itsWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * This method will wait till element is visible and then click on it
     *
     * @param element - webelement
     */
    protected void waitAndClickWhenElementIsVisible(final WebElement element) {
        final String message = String.format("FAIL : Element with these details is NULL : '%s'", element);
        assertCheckIfNotNull(message, element);
        itsWait.until(ExpectedConditions.visibilityOf(element)).click();
    }

    /**
     * This method is to click clear and then populate the provided string in an element
     *
     * @param element        - webelement
     * @param textToPopulate - value that needs to be replaced from xpath
     */
    protected void clickClearSendkeys(final WebElement element, final String textToPopulate) {
        waitUntilElementIsClickable(element);
        waitAndClickWhenElementIsVisible(element);
        element.clear();
        element.sendKeys(textToPopulate);
    }

    /**
     * This method clicks on the element witht he given xpath
     *
     * @param xpath - xpath
     */
    protected void clickOnElementFromXpath(final String xpath) {
        itsDriver.findElement(By.xpath(xpath)).click();
    }

    /**
     * This method creates a WebDriverWait
     *
     * @param timeOutInSeconds - Time Out In Seconds
     */
    protected WebDriverWait createWebWaitDriver(final long timeOutInSeconds) {
        return new WebDriverWait(itsDriver, timeOutInSeconds);
    }

    /**
     * This method checks if the object is not null
     *
     * @param message              - message if the object is NULL
     * @param objectToCheckNotNull - an Object to check
     */
    protected void assertCheckIfNotNull(final String message, final Object objectToCheckNotNull) {
        Assert.assertNotNull(message, objectToCheckNotNull);
    }

    /**
     * This method waits until the element is visible
     *
     * @param element - WebElement
     */
    protected WebElement waitUntilElementIsVisible(final WebElement element) {
        final String message = String.format("FAIL : Element with these details is NULL : '%s'", element);
        assertCheckIfNotNull(message, element);
        return itsWait.until(ExpectedConditions.visibilityOf(element));
    }


    protected WebElement getElementAfterReplaceValueFromXpath(
            final String xpath,
            final String target,
            final String replacement) {
        return itsDriver.findElement(By.xpath(xpath.replace(target, replacement)));
    }


    protected void clickUsingJavaScript(final WebElement element) {
        try {
            final JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("arguments[0].click();", element);
        } catch (final StaleElementReferenceException exception) {
            assertFail(String.format("Element is not attached to the page document : %s", element),
                    exception);
        } catch (final NoSuchElementException exception) {
            assertFail(String.format("Element was not found in DOM : %s", element), exception);
        } catch (final Exception exception) {
            assertFail(String.format("Unable to click on element : ' %s '", element), exception);
        }
    }


    protected void checkIfTextOfAnElementIsNotPresentInAList(
            final List<WebElement> listOfElements,
            final String textToCheck) {
        final HashSet<String> textFromElements = new HashSet<String>();

        for (final WebElement element : listOfElements) {
            textFromElements.add(element.getText().trim());
        }

        assertCheckIfFalse(String.format("FAIL: value '%s' is present in the list : %s ", textToCheck,
                textFromElements), textFromElements.contains(textToCheck));
    }

    protected void assertFail(final String message, final Throwable realCause) {
        Assert.fail(message);
    }

    protected boolean checkElementVisibilityNoWait(final WebElement statusElement) {
        try {
            itsNoWait.until(ExpectedConditions.visibilityOf(statusElement));
        } catch (final TimeoutException ex) {
            return false;
        }
        return true;
    }


    public void implicitWait(final int time) {
        itsDriver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }


    public void waitForVisible(final WebElement webElement) {
        itsWait.until(ExpectedConditions.visibilityOf(webElement));
    }


    public void enterValue(final WebElement editBox, final String valueToType) {
        waitForVisible(editBox);
        editBox.clear();
        editBox.sendKeys(valueToType);
    }


    public void selectRadioButton(final WebElement rdoElement) {
        waitForVisible(rdoElement);
        if (!rdoElement.isSelected()) {
            rdoElement.click();
        }
    }


    public void selectListByVisibleText(final WebElement listElement, final String optionText) {
        waitForVisible(listElement);
        final Select selectList = new Select(listElement);
        Log.info("Select list option: " + optionText);
        selectList.selectByVisibleText(optionText);
    }


    public void clickElement(final WebElement webElement) {
        waitForVisible(webElement);
        waitUntilElementIsClickable(webElement);
        webElement.click();
    }


    public void assertElementIsVisible(final WebElement webElement, final String message) {
        //	waitForVisible(webElement);
        Assert.assertTrue(message, webElement.isDisplayed());
    }

    public void assertPageURL(final String URL) {
        String actualURL = itsDriver.getCurrentUrl();
        Assert.assertTrue(actualURL.contains(URL));
    }


    public void assertElementIsVisible(final By locator, final String failedMessage) {
        int retry = 0;
        while (retry < Constants.RETRY_COUNT) {
            try {
                final WebElement element = waitUntilElementIsVisible(itsDriver.findElement(locator));
                Assert.assertTrue(failedMessage, element.isDisplayed());
                return;
            } catch (final StaleElementReferenceException ex) {
                retry++;
            } catch (final TimeoutException e) {
                getDriver().navigate().refresh();
                retry++;
            } catch (final NoSuchElementException e) {
                getDriver().navigate().refresh();
                retry++;
            }
        }
        assertFail(failedMessage, null);
    }


    public void assertTextIsVisible(final WebElement webElement, final String message, final String text) {
        final String elementText = webElement.getText();
        Assert.assertTrue(message + elementText, elementText.contains(text));
    }

    public void assertIgnoreCaseTextIsVisible(
            final WebElement webElement,
            final String errorMessage,
            final String successMessage) {
        waitForVisible(webElement);
        final String elementText = webElement.getText();
        Assert.assertTrue(errorMessage + elementText, successMessage.equalsIgnoreCase(elementText));
    }

    public void assertContainsTextIsVisible(
            final WebElement webElement,
            final String errorMessage,
            final String successMessage) {
        waitForVisible(webElement);
        final String elementText = webElement.getText();
        Assert.assertTrue(errorMessage + elementText, successMessage.contains(elementText));
    }


    public void assertAttributeIsVisible(
            final WebElement webElement,
            final String message,
            final String text,
            final String attribute) {
        final String elementAttributeValue = webElement.getAttribute(attribute);
        Assert.assertTrue(message + elementAttributeValue, elementAttributeValue.contains(text));
    }


    public void clearElement(final WebElement webElement) {
        webElement.clear();
    }


    public void scrollIntoView(final WebElement element) {
        final JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }


    public void clickElmentUsingTextFromListOfElements(
            final List<WebElement> listOfElements,
            final String textOfElement) {
        final List<String> entries = new ArrayList<>();
        for (final WebElement element : listOfElements) {
            final String text = element.getText();
            if (textOfElement.equals(text)) {
                clickElement(element);
                return;
            } else {
                entries.add(text);
            }
        }
        Assert.fail("Failed to locate '" + textOfElement + "' in the list: " + entries);
    }


    public void assertElementText(final WebElement element, final String expectedText) {
        final String message = String.format("FAIL : Element is not having this text '%s'", expectedText);
        final String actualText = element.getText();
        if (actualText != null)
            assertEquals(message, actualText, expectedText);
        else {
            assertEquals("Expected text: '" + message + "' But having text " + actualText, actualText,
                    expectedText);
        }
    }

    protected void checkIfTextSubstringIsContainedInAList(
            final List<WebElement> listOfElements,
            final String textToCheck) {
        final Set<String> textFromElements = new HashSet<String>();

        for (final WebElement element : listOfElements) {
            final String txt = element.getText();
            if (txt.contains(textToCheck))
                return;

            textFromElements.add(txt.trim());
        }
        Assert.fail(String.format("FAIL: value substring '%s' is not present in the list : %s ", textToCheck,
                textFromElements));
    }

    public void executeScript(final String jsScript) {
        final JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(jsScript);
    }
}