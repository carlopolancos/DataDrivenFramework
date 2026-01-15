package testCases;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BankManagerLoginTest extends BaseTest {

    @Test
    public void loginAsBankManager(){
        Browser browser = getBrowser("chrome");
        navigate(browser, "https://www.way2automation.com/angularjs-protractor/banking/#/login");
        click("bmlBtn_CSS");
        Assert.assertTrue(isElementPresent("addCustomerBtn_CSS"), "Bank Manager is not logged in");
    }
}
