package testCases;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Dialog;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.Constants;
import utilities.DataProviders;
import utilities.DataUtil;
import utilities.ExcelReader;

import java.util.Hashtable;

public class AddCustomerTest extends BaseTest {


    @Test(dataProviderClass = DataProviders.class, dataProvider = "bankManagerDP")
    public void addCustomerTest(Hashtable<String, String> data) {
        ExcelReader excel = new ExcelReader(Constants.SUITE1_XL_PATH);
        String testSuiteName = "BankManagerSuite";
        String testCaseName = "AddCustomerTest";

        DataUtil.checkExecution(testSuiteName, testCaseName, data.get("Runmode"), excel);
        Browser browser = getBrowser(data.get("browser"));

        navigate(browser, Constants.URL);
        click("bmlBtn_CSS");
        click("addCustomerBtn_CSS");
        fill("firstNameInput_CSS", data.get("firstname"));
        fill("lastNameInput_CSS", data.get("lastname"));
        fill("postCodeInput_CSS", data.get("postcode"));
        click("submitCustomerBtn_CSS");
    }
}
