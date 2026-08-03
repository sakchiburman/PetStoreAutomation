package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    @Override
    public void onStart(ITestContext testContext) {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
                .format(new Date());

        repName = "Test-Report-" + timeStamp + ".html";

        // Specify location of the report
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);

        // Report configuration
        sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject");
        sparkReporter.config().setReportName("Pet Store Users API");
        sparkReporter.config().setTheme(Theme.DARK);

        // Create ExtentReports object
        extent = new ExtentReports();

        // Attach reporter
        extent.attachReporter(sparkReporter);

        // System information
        extent.setSystemInfo("Application", "Pet Store Users API");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "pavan");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test = extent.createTest(result.getName());

        test.assignCategory(result.getMethod().getGroups());

        test.createNode(result.getName());

        test.log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test = extent.createTest(result.getName());

        test.assignCategory(result.getMethod().getGroups());

        test.createNode(result.getName());

        test.log(Status.FAIL, "Test Failed");

        if (result.getThrowable() != null) {
            test.log(Status.FAIL, result.getThrowable().getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test = extent.createTest(result.getName());

        test.assignCategory(result.getMethod().getGroups());

        test.createNode(result.getName());

        test.log(Status.SKIP, "Test Skipped");

        if (result.getThrowable() != null) {
            test.log(Status.SKIP, result.getThrowable().getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext testContext) {

        extent.flush();
    }
}