package extentListeners;

import base.BaseTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.microsoft.playwright.Page;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

public class ExtentManager {

    private static ExtentReports extent;
    public static String fileName;

    public static ExtentReports createInstance(String filename) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(filename);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setDocumentTitle(filename);
        htmlReporter.config().setReportName("Automation Test Results");
        htmlReporter.config().setTheme(Theme.DARK);

        ExtentReports extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Automation Tester", "Shiso Sagun");
        extent.setSystemInfo("Build No:", "1234");
        extent.setSystemInfo("Organization", "Way2Automation");
        System.out.println();

        return extent;
    }

    public static void captureScreenshot() throws IOException {
        Date d = new Date();
        fileName = d.toString().replace(":", " ").replace(" ", "_") + ".jpg";
        BaseTest.getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./reports/" + fileName)));
    }
}
