package testCases;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void doLogin() {
        Browser browser = getBrowser("chrome");
        navigate(browser, "https://www.google.com/");
        fill("searchBox", "Hello Playwright");
    }

    @Test
    public void doGmailLogin() {
        Browser browser = getBrowser("firefox");
        navigate(browser, "https://accounts.google.com/v3/signin/identifier?continue=https%3A%2F%2Faccounts.google.com%2F&dsh=S-64759537%3A1768352715286855&followup=https%3A%2F%2Faccounts.google.com%2F&ifkv=AXbMIuDRz0eCxl0T5xhG3ZP9Q_Ix1clmPTtdO3Ni8RR939X2fGpEhQC1EqHRdYjsA3dAttvRJua7kw&passive=1209600&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
        fill("username", "carlo.polancos@awsys-i.com");
//        click("emailNext");
//        fill("password", "asokdmoqwmdoq");
//        click("passwordNext");
    }
}
