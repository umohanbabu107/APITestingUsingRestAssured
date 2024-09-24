package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter; // This is used to configure the UI
    public ExtentReports extentReports; // Populate common info on report
    public ExtentTest test; // Creating test case entries in the report and update the status of test methods

    @Override
    public void onStart(final ITestContext context){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        final ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        final String timestamp = time.format(formatter);
        this.sparkReporter = new ExtentSparkReporter(Paths.get("target/emailableReport/report_"+timestamp+".html").toString());
        this.sparkReporter.config().setDocumentTitle("Helios API Tests Reports");
        this.sparkReporter.config().setReportName("API Tests results");
        this.sparkReporter.config().setTheme(Theme.DARK);


        this.extentReports = new ExtentReports();
        this.extentReports.attachReporter(this.sparkReporter);

        this.extentReports.setSystemInfo("Engineer", "Mohan");
    }
    @Override
    public void onTestStart(final ITestResult result){
        this.test = this.extentReports.createTest(result.getName());
    }
    @Override
    public void onTestSuccess(final ITestResult result){
        // This will create a test in the report
        this.test.log(Status.PASS, "Testcase passed is "+result.getName());
    }
    @Override
    public void onTestFailure(final ITestResult result){
        test.log(Status.FAIL, "Test failed "+result.getName());
        test.log(Status.FAIL, "Error:"+result.getThrowable().getMessage());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        result.getThrowable().printStackTrace(pw);
        String stackTrace = sw.toString();
        test.log(Status.FAIL, "<details><summary>Click to see the stack trace</summary>" +
                "<pre>" + stackTrace + "</pre></details>");
    }
    @Override
    public void onTestSkipped(final ITestResult result){
        this.test.log(Status.SKIP, "Testcase skipped is "+result.getName());

    }
    @Override
    public void onFinish(final ITestContext context){
        this.extentReports.flush();
    }
}
