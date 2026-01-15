package testCases;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import org.testng.annotations.Test;
import utilities.Constants;
import utilities.DataProviders;
import utilities.DataUtil;
import utilities.ExcelReader;

import java.util.Hashtable;

public class OpenAccountTest extends BaseTest {

    @Test(dataProviderClass = DataProviders.class, dataProvider = "bankManagerDP")
    public void openAccountTest(Hashtable<String, String> data) {
        ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
        String testSuiteName = "BankManagerSuite";
        String testCaseName = "OpenAccountTest";

        DataUtil.checkExecution(testSuiteName, testCaseName, data.get("Runmode"), excel);
        Browser browser = getBrowser(data.get("browser"));

        navigate(browser, Constants.URL);
        click("bmlBtn_CSS");
        click("openAccountBtn_CSS");
        select("customerSelect_CSS", data.get("customer"));
        select("currencySelect_CSS", data.get("currency"));
        click("processBtn_CSS");
    }
}
