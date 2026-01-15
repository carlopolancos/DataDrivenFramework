package extentListeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.testng.*;

import java.io.IOException;
import java.util.Date;

public class ExtentListeners implements ITestListener, ISuiteListener {

    static Date date = new Date();
    static String filename = "Extent_" + date.toString().replace(":", " ").replace(" ", "_");
    private static ExtentReports extent = ExtentManager.createInstance(".\\reports\\"+filename);
    public static ExtentTest test;

    public static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();

    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName() + " @TestCase: " + result.getMethod().getMethodName());
        testReport.set(test);
    }

    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "<b> TEST CASE: " + methodName.toUpperCase() + " PASSED </b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
        testReport.get().pass(m);
    }

    public void onTestFailure(ITestResult result) {
        try {
            ExtentManager.captureScreenshot();
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        String methodName = result.getMethod().getMethodName();
        String logText = "<b> TEST CASE: " + methodName.toUpperCase() + " FAILED </b>";

        testReport.get().fail("<b><font color=red>" + "Screenshot of failure" + "</font></b><br>",
                MediaEntityBuilder.createScreenCaptureFromPath(ExtentManager.fileName).build());

        Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
        testReport.get().log(Status.FAIL, m);
    }

    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "<b> TEST CASE: " + methodName.toUpperCase() + " SKIPPED </b>";
        Markup m = MarkupHelper.createLabel(logText, ExtentColor.YELLOW);
        testReport.get().skip(m);
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {

    }

    public void onFinish(ITestContext context) {
        if (extent != null){
            extent.flush();
        }
    }
}