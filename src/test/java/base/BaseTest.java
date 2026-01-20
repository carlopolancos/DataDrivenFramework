package base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;
import extentListeners.ExtentListeners;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Properties;

public class BaseTest {

    private Playwright playwright;
    public Browser browser;
    public BrowserContext browserContext;
    public Page page;

    private static Properties OR = new Properties();
    private static FileInputStream fis;
    private final Logger log = LogManager.getLogger(this.getClass());

    private static ThreadLocal<Playwright> pw = new ThreadLocal<>();
    private static ThreadLocal<Browser> br = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> ctx = new ThreadLocal<>();
    private static ThreadLocal<Page> pg = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return pw.get();
    }

    public static Browser getBrowser() {
        return br.get();
    }

    public static BrowserContext getBrowserContext() {
        return ctx.get();
    }

    public static Page getPage() {
        return pg.get();
    }

    @BeforeSuite
    public void setUp() {
        Configurator.initialize(null, "./src/test/resources/log4j2.properties");
        log.info("--- New Test Suite Execution Started ---");

        try {
            fis = new FileInputStream("./src/test/resources/properties/OR.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.out);
            log.error("File not found at path './src/test/resources/properties/OR.properties'");
        }

        try {
            OR.load(fis);
            log.info("OR properties file loaded");
        } catch (IOException e) {
            e.printStackTrace(System.out);
            log.error("Error loading file");
        }
    }

    public Browser getBrowser(String browserName){
        try {
            playwright = Playwright.create();
            pw.set(playwright);
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("--start-maximized");

            browser = switch (browserName) {
                case "chrome" -> {
                    log.info("Launching Chrome Browser");
                    yield getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false)
                            .setArgs(arguments));
                }
                case "firefox" -> {
                    log.info("Launching Firefox Browser");
                    yield getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setChannel("firefox").setHeadless(false)
                            .setArgs(arguments));
                }
                case "webkit" -> {
                    log.info("Launching Webkit Browser");
                    yield getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setChannel("webkit")
                            .setArgs(arguments));
                }
                case "headless" -> {
                    log.info("Launching Headless Browser");
                    yield getPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                            .setArgs(arguments));
                }
                default -> {
                    log.info("Invalid browser name");
                    throw new IllegalArgumentException("Invalid browser name");
                }
            };

            return browser;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public void navigate(Browser browser, String url){
        this.browser = browser;
        br.set(browser);
        browserContext = getBrowser().newContext(new Browser.NewContextOptions().setViewportSize(null));
        ctx.set(browserContext);
        page = getBrowserContext().newPage();
        pg.set(page);
        getPage().navigate(url);
        log.info("Navigated to: " + url);

        getPage().onDialog(dialog -> {
            dialog.accept();
            System.out.println(dialog.message());
        });
    }

    public void click(String locatorKey) {
        String selector = OR.getProperty(locatorKey);
        try {
            getPage().locator(selector).click();
            log.info("Clicking on element " + locatorKey + "(" + selector + ")");
            ExtentListeners.testReport.get().info("Clicking on element " + locatorKey + "(" + selector + ")");
        } catch (Throwable t) {
            log.error("Error while click an element " + locatorKey + "(" + selector + "): " + t.getMessage());
            ExtentListeners.testReport.get().fail("Error while click on an element " + locatorKey + "(" + selector + "): " + t.getMessage());
            Assert.fail(t.getMessage());
        }
    }

    public void fill(String locatorKey, String value) {
        String selector = OR.getProperty(locatorKey);
        try {
            getPage().locator(selector).fill(value);
            log.info("Typing on element " + locatorKey + "(" + selector + ") and entered the value: " + value);
            ExtentListeners.testReport.get().info("Typing on element " + locatorKey + "(" + selector + ") and entered the value: " + value);
        } catch (Throwable t) {
            log.error("Error while filling \"" + value + "\" on element " + locatorKey + "(" + selector + "): " + t.getMessage());
            ExtentListeners.testReport.get().fail("Error while filling \"" + value + "\" on element " + locatorKey + "(" + selector + "): " + t.getMessage());
            Assert.fail(t.getMessage());
        }
    }

    public void select(String locatorKey, String value) {
        String selector = OR.getProperty(locatorKey);
        try {
            getPage().locator(selector).selectOption(new SelectOption().setLabel(value));
            log.info("Selecting on element " + locatorKey + "(" + selector + ") and selected the value: " + value);
            ExtentListeners.testReport.get().info("Selecting on element " + locatorKey + "(" + selector + ") and selected the value: " + value);
        } catch (Throwable t) {
            log.error("Error while selecting \"" + value + "\" on element " + locatorKey + "(" + selector + "): " + t.getMessage());
            ExtentListeners.testReport.get().fail("Error while selecting \"" + value + "\" on element " + locatorKey + "(" + selector + "): " + t.getMessage());
            Assert.fail(t.getMessage());
        }
    }

    public boolean isElementPresent(String locatorKey) {
        String selector = OR.getProperty(locatorKey);
        Locator element = getPage().locator(selector);
        try {
            getPage().waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(5000));
            if (element.isVisible()) {
                log.info("Found element " + locatorKey + "(" + selector + ")");
                ExtentListeners.testReport.get().info("Finding element " + locatorKey + "(" + selector + ")");
                return true;
            } else {
                log.info("Cannot find element " + locatorKey + "(" + selector + ")");
                ExtentListeners.testReport.get().info("Finding element " + locatorKey + "(" + selector + ")");
                return false;
            }
        } catch (Throwable t) {
            log.error("Error while finding element " + locatorKey + "(" + OR.getProperty(locatorKey) + "): " + t.getMessage());
            ExtentListeners.testReport.get().fail("Error while finding element " + locatorKey + "(" + OR.getProperty(locatorKey) + "): " + t.getMessage());
            return false;
        }
    }

    @AfterMethod
    public void quit() {
        if (getPage() != null) {
            getPage().close();
        }
        if (getBrowser() != null) {
            getBrowser().close();
        }
    }

    @AfterSuite
    public void quit2() {
        playwright.close();
    }
}