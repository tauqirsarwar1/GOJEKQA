package com.gojek.lib;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Properties;
import java.util.logging.Logger;


public final class WebDriverFactory
{
	private final static Logger Log = Logger.getLogger(WebDriverFactory.class.getName());

	private WebDriverFactory()
	{
		// Should not be invoked directly.
	}

	public static WebDriver createWebDriver()
		throws ScumberException
	{
		final Properties props = PropertiesLoader.getInstance().load("browser.properties");

		final String browserDefault = props.getProperty("browser");
		final String browser = StringUtils.trimToEmpty(System.getProperty("browser", browserDefault));

		final String headlessDefault = props.getProperty("headless");
		final boolean headless = Boolean.parseBoolean(System.getProperty("headless", headlessDefault));

		return createWebDriver(browser, headless);
	}

	public static WebDriver createWebDriver(final String browser, final boolean headless)
		throws ScumberException
	{
		Log.info("Creating browser instance: " + browser + ", headless? " + headless);
		switch (browser)
		{
			case "ie":
				return createIeWebDriver();

			case "chrome":
				return createChromeWebDriver(headless);

			case "firefox":
				return createFirefoxWebDriver();
		}

		throw new ScumberException("Unknown browser type: " + browser);
	}

	private static WebDriver createIeWebDriver()
	{
		System.setProperty("webdriver.ie.driver", "src/test/resources/attachments/IEDriverServer.exe");

		final DesiredCapabilities caps = DesiredCapabilities.internetExplorer();

		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		caps.setJavascriptEnabled(true);
		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		caps.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		caps.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
		caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);

		return new InternetExplorerDriver(caps);
	}

	private static WebDriver createChromeWebDriver(final boolean headless)
	{
		final String nativeOS = System.getProperty("os.name").toLowerCase();
		if (nativeOS.indexOf("linux") >= 0)
		{
			System.setProperty("webdriver.chrome.driver", "src/test/resources/attachments/chromedriver");
		}
		else if (nativeOS.indexOf("mac") >= 0)
		{
			System.setProperty("webdriver.chrome.driver", "src/test/resources/attachments/chromedriver");
		}
		else
		{
			System.setProperty("webdriver.chrome.driver", "src/test/resources/attachments/chromedriver.exe");
		}

		final ChromeOptions options = new ChromeOptions();
		options.addArguments("--test-type");
		options.addArguments("--no-sandbox");
		if (headless)
		{
			options.addArguments("headless");
			options.addArguments("window-size=1200x600");
		}

		final WebDriver driver = new ChromeDriver(options);
		return driver;
	}

	private static WebDriver createFirefoxWebDriver()
	{
		System.setProperty("webdriver.gecko.driver", "src/test/resources/attachments/geckodriver.exe");
		return new FirefoxDriver(DesiredCapabilities.firefox());
	}
}
