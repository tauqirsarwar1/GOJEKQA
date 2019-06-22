//=====================================================================================================================
//
// You are hereby placed on notice that the software, its related technology and services may be covered by one or
// more United States ("US") and non-US patents. A listing that associates patented and patent-pending products
// included in the software, software updates, their related technology and services with one or more patent numbers
// The association of products-to-patent numbers at the Patent Notice may not be an exclusive listing of associations,
// and other unlisted patents or pending patents may also be associated with the products. Likewise, the patents or
// pending patents may also be associated with unlisted products. You agree to regularly review the products-to-patent
// number(s) association at the Patent Notice to check for updates.
//=====================================================================================================================

package com.gojek.stepdefs;

import com.gojek.lib.ScumberException;
import com.gojek.lib.WebDriverActions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

public class Hooks
{
	private static final Logger Log	= Logger.getLogger(Hooks.class.getName());

	private WebDriver itsDriver;

	public Hooks()
	{
		Log.info("Constructor: Hooks");
	}

	@Before
	public void before(final Scenario scenario)
		throws ScumberException
	{
		itsDriver = WebDriverActions.openBrowser(scenario);
	}

	@After
	public void after(final Scenario scenario)
	{
		WebDriverActions.closeBrowser(scenario, itsDriver);
		itsDriver = null;
	}
}
