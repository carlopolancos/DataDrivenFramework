package rough;

import utilities.Constants;
import utilities.ExcelReader;
import utilities.DataUtil;

public class CheckingRunModes {

    public static void main(String[] args){

        String suiteName = "BankManagerSuite";
        boolean suiteRunMode = DataUtil.isSuiteRunnable(suiteName);
        System.out.println(suiteRunMode);

        String testCaseName = "OpenAccountTest";
        boolean testRunMode = DataUtil.isTestRunnable(testCaseName, new ExcelReader(Constants.SUITE1_XL_PATH));
        System.out.println(testRunMode);

    }
}