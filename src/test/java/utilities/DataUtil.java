package utilities;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import java.util.Hashtable;
import java.util.Objects;

public class DataUtil {

    public static void checkExecution(String testSuiteName, String testCaseName, String dataRunMode, ExcelReader excel) {
        if (!isSuiteRunnable(testSuiteName)) {
            throw new SkipException("Skipping the test : " + testCaseName + " as the Runmode of Test Suite : " + testSuiteName + " is NO");
        }
        if (!isTestRunnable(testCaseName,excel)) {
            throw new SkipException("Skipping the test : " + testCaseName + " as the Runmode of Test Case : " + testCaseName + " is NO");
        }
        if(dataRunMode.equalsIgnoreCase(Constants.RUNMODE_NO)){
            throw new SkipException("Skipping the test : " + testCaseName + " as the Runmode to Data set is NO");
        }
    }

    public static boolean isSuiteRunnable(String suiteName) {
        ExcelReader excel = new ExcelReader(Constants.SUITE_XL_PATH);
        int rows = excel.getRowCount(Constants.SUITE_SHEET);
        for (int rowNum = 2; rowNum <= rows; rowNum++) {
            String data = excel.getCellData(Constants.SUITE_SHEET, Constants.SUITENAME_COL, rowNum);
            if (data.equals(suiteName)) {
                String runmode = excel.getCellData(Constants.SUITE_SHEET, Constants.RUNMODE_COL, rowNum);
                if (runmode.equals(Constants.RUNMODE_YES))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    public static boolean isTestRunnable(String testCaseName, ExcelReader excel) {
        int rows = excel.getRowCount(Constants.TESTCASE_SHEET);
        for (int rowNum = 2; rowNum <= rows; rowNum++) {
            String data = excel.getCellData(Constants.TESTCASE_SHEET, Constants.TESTCASE_COL, rowNum);
            if (data.equals(testCaseName)) {
                String runmode = excel.getCellData(Constants.TESTCASE_SHEET, Constants.RUNMODE_COL, rowNum);
                if (runmode.equals(Constants.RUNMODE_YES))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }

    @DataProvider
    public static Object[][] getData(String testCase, ExcelReader excel) {
        int totalRows = excel.getRowCount(Constants.DATA_SHEET);
        int totalCols = excel.getColumnCount(Constants.DATA_SHEET);
        System.out.println("Total totalRows is: " + totalRows + "\nTotal columns is: " + totalCols);

        //FIND THE TEST CASE STARTING ROW
        String testName = testCase;
        int testCaseRowNum = 1;
        for(testCaseRowNum = 1; testCaseRowNum<= totalRows; testCaseRowNum++){
            String testCaseName = excel.getCellData(Constants.DATA_SHEET, 0, testCaseRowNum);
            if (testCaseName.equalsIgnoreCase(testName)){
                break;
            }
        }
        System.out.println("Test case starts from row num: " + testCaseRowNum);

        //CHECKING TOTAL ROWS IN TESTCASE
        int dataStartRowNum = testCaseRowNum + 2;
        int testRows = 0;
        while (!Objects.equals(excel.getCellData(Constants.DATA_SHEET, 0, dataStartRowNum + testRows), "")) {
            testRows++;
        }
        System.out.println("Total number of test cases are: " + testRows);

        //CHECKING TOTAL ROWS IN TESTCASE
        int colStartColNum = testCaseRowNum + 1;
        int testCols = 0;
        while (!Objects.equals(excel.getCellData(Constants.DATA_SHEET, testCols, colStartColNum), "")) {
            testCols++;
        }
        System.out.println("Total number of columns are: " + testCols);

        Object[][] data = new Object[testRows][1];

        //PRINTING DATA
        int i = 0; //FOR HASHTABLE
        for (int rNum = dataStartRowNum; rNum < (dataStartRowNum+testRows); rNum++){
            Hashtable<String, String> table = new Hashtable<String, String>(); //FOR HASHTABLE
            for(int cNum = 0; cNum < testCols; cNum++){
                String testData = excel.getCellData(Constants.DATA_SHEET, cNum, rNum);
                if (testCols-cNum != 1){
                    System.out.print(testData + " - ");
                } else {
                    System.out.println(testData);
                }

                //FOR HASHTABLE
                String colName = excel.getCellData(Constants.DATA_SHEET, cNum, colStartColNum);
                table.put(colName, testData);
            }
            data[i][0] = table;
            i++;
        }

//        Object[][] data = new Object[testRows][testCols];
//        for (int rNum = dataStartRowNum; rNum < (dataStartRowNum+testRows); rNum++){
//            Hashtable<String, String> table = new Hashtable<String, String>();
//            for(int cNum = 0; cNum < testCols; cNum++){
//                String testData = excel.getCellData(Constants.DATA_SHEET, cNum, rNum);
//                System.out.println(testData);
//                data[rNum-dataStartRowNum][cNum] = testData;
//            }
//        }

        return data;
    }
}