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

public class EntryCheckReportManager implements ITestListener {
    ExtentSparkReporter sparkReporter; // This class is used to configure UI of extent reports
    ExtentReports extentReports; // Which is used to create the entries in the report
    ExtentTest test;

    @Override
    public void onStart(final ITestContext context){
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        final ZonedDateTime time = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
        final String timestamp = time.format(formatter);
        this.sparkReporter = new ExtentSparkReporter(Paths.get("target/entryCheckReports/report_"+timestamp+".html").toString());

        String logoPath = "src/resources/companyLogo/trimblelogo.png"; // Path to your company logo


        this.sparkReporter.config().setDocumentTitle("Entry Check API Test Reports");
        this.sparkReporter.config().setReportName("API test report");
        this.sparkReporter.config().setTheme(Theme.DARK);

        String customCss = ".report-name::before { content: ''; display: block; background-image: url('" + logoPath + "'); " +
                "background-size: contain; background-repeat: no-repeat; height: 50px; margin-bottom: 20px; }";
        this.sparkReporter.config().setCss(customCss);


        this.extentReports = new ExtentReports();
        this.extentReports.attachReporter(this.sparkReporter);
        this.extentReports.setSystemInfo("Owner", "Mohan");
        this.extentReports.setSystemInfo("Environment", "Production");
    }
    @Override
    public void onTestStart(ITestResult result){
        test = this.extentReports.createTest(result.getName());
    }
    @Override
    public void onTestSuccess(ITestResult result){
        test.log(Status.PASS, "Test passed "+result.getName());
    }
    public void onTestFailure(ITestResult result){
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
    public void onTestSkipped(ITestResult result){
        test.log(Status.SKIP, "Test skipped "+result.getName());
    }
    @Override
    public void onFinish(ITestContext context){
        extentReports.flush();
    }

}
