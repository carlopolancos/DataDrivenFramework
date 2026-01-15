package rough;

import base.BaseTest;
import org.testng.annotations.Test;
import utilities.Constants;
import utilities.ExcelReader;

import java.util.Arrays;
import java.util.Objects;

public class ReadingExcelData extends BaseTest {


    public static void main(String[] args){
        ExcelReader excel = new ExcelReader("./src/test/resources/excel/BankManagerSuite.xlsx");
        int rows = excel.getRowCount(Constants.DATA_SHEET);
        int cols = excel.getColumnCount(Constants.DATA_SHEET);
        System.out.println("Total rows is: " + rows + "\nTotal columns is: " + cols);

        //FIND THE TEST CASE STARTING ROW
        String testName = "OpenAccountTest";
//        String testName = "AddCustomerTest";
        int testCaseRowNum = 1;
        for(testCaseRowNum = 1; testCaseRowNum<=rows; testCaseRowNum++){
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

        //PRINTING DATA
        for (int rNum = dataStartRowNum; rNum < (dataStartRowNum+testRows); rNum++){
            for(int cNum = 0; cNum < testCols; cNum++){
                System.out.println(excel.getCellData(Constants.DATA_SHEET, cNum, rNum));
            }
        }
    }
}
