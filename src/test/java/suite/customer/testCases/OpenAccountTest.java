package suite.customer.testCases;

import org.testng.annotations.Test;
import utilities.Constants;
import utilities.DataProviders;
import utilities.DataUtil;
import utilities.ExcelReader;

import java.util.Hashtable;

public class OpenAccountTest {

    @Test(dataProviderClass = DataProviders.class, dataProvider = "customerDP")
    public void openAccountTest(Hashtable<String, String> data) {

        ExcelReader excel = new ExcelReader(Constants.SUITE2_XL_PATH);
        String suiteName = "CustomerSuite";
        String testName = "OpenAccountTest";
        System.out.println("Suite " + suiteName + " is runnable: " + DataUtil.isSuiteRunnable(suiteName));
        System.out.println("Test " + testName + " is runnable: " + DataUtil.isTestRunnable(testName, excel));
        System.out.println("Runmode for data " + data.get("Runmode"));
        DataUtil.checkExecution(suiteName, testName, data.get("Runmode"), excel);
    }
}
