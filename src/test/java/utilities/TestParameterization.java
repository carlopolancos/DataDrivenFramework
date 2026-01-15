package utilities;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;
import java.util.Objects;

public class TestParameterization {

//    @Test(dataProvider = "getData")
//    public void testData(String runmode, String firstName, String lastName, String postalCode) {
//        System.out.println(runmode + " - " + firstName + " - " + lastName + " - " + postalCode);
//    }

//    @Test(dataProvider = "getData")
//    public void testData(String runmode, String customer, String currency) {
//        System.out.println(runmode + " - " + customer + " - " + currency);
//    }

//    @Test(dataProvider = "getData")
//    public void testData(Hashtable<String, String> data) {
//        System.out.println(data.get("runmode") + " - " + data.get("customer") + " - " + data.get("currency"));
//    }

    @Test(dataProvider = "getData")
    public void testData(Hashtable<String, String> data) {
        System.out.println(data.get("runmode") + " - " + data.get("firstname") + " - "
                + data.get("lastname") + " - " + data.get("postcode"));
    }


    @DataProvider
    public Object[][] getData() {
        ExcelReader excel = new ExcelReader("./src/test/resources/excel/BankManagerSuite.xlsx");
        int totalRows = excel.getRowCount(Constants.DATA_SHEET);
        int totalCols = excel.getColumnCount(Constants.DATA_SHEET);
        System.out.println("Total totalRows is: " + totalRows + "\nTotal columns is: " + totalCols);

        //FIND THE TEST CASE STARTING ROW
//        String testName = "OpenAccountTest";
        String testName = "AddCustomerTest";
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
                System.out.println(testData);

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