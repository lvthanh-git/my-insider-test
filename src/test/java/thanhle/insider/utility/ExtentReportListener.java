package thanhle.insider.utility;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;

import java.util.concurrent.ConcurrentHashMap;

import thanhle.insider.customazation.DriverManager;

public class ExtentReportListener implements IExecutionListener, ITestListener {

	private static volatile ExtentReports extent;
	private static final Object EXTENT_LOCK = new Object();

	private static ThreadLocal<ExtentTest> currentTest = new ThreadLocal<>();

	private static final ConcurrentHashMap<String, ExtentTest> suiteNodes = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, ExtentTest> classNodes = new ConcurrentHashMap<>();
	private static boolean systemInfoAdded = false;

	private static ExtentReports getExtent() {
		if (extent == null) {
			synchronized (EXTENT_LOCK) {
				if (extent == null) {
//					String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
					String path = "ExtentReport.html";

					ExtentSparkReporter spark = new ExtentSparkReporter(path);
					spark.config().setDocumentTitle("Insider Test Report");
					spark.config().setReportName("Insider Test Execution");
					spark.config().setTheme(Theme.STANDARD);

					extent = new ExtentReports();
					extent.attachReporter(spark);
				}
			}
		}
		return extent;
	}

	@Override
	public void onExecutionStart() {
		getExtent();
	}

	@Override
	public void onExecutionFinish() {
		if (extent != null)
			extent.flush();
	}

	@Override
	public void onFinish(ITestContext context) {
		if (!systemInfoAdded) {
	        String baseUrl = safeParam(context, "baseUrl");
	        String webURL  = safeParam(context, "webURL");

	        if (baseUrl != null && !baseUrl.isEmpty()) getExtent().setSystemInfo("API Environment", baseUrl);
	        if (webURL != null && !webURL.isEmpty()) getExtent().setSystemInfo("WEB Environment", webURL);

	        systemInfoAdded = true;
	    }
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentReports ext = getExtent();

		String testName = result.getTestContext().getName();
		String className = result.getTestClass().getRealClass().getSimpleName();
		String method = result.getMethod().getMethodName();

		String testType = safeParam(result, "test-type");
		String browser = safeParam(result, "browser");

		String displayName = method
				+ ((browser != null && !browser.isEmpty()) ? " [" + browser.toUpperCase() + "]" : "");

		ExtentTest testNode = suiteNodes.computeIfAbsent(testName, n -> ext.createTest(n));

		String classKey = testName + "::" + className + ((browser != null) ? "::" + browser.toUpperCase() : "");
		ExtentTest classNode = classNodes.computeIfAbsent(classKey,
				k -> testNode.createNode(className + ((browser != null) ? " [" + browser.toUpperCase() + "]" : "")));

		ExtentTest testMethodNode = classNode.createNode(displayName);
		currentTest.set(testMethodNode);

		if (testType != null && !testType.isEmpty())
			testMethodNode.assignCategory(testType.toUpperCase());

		testMethodNode.log(Status.INFO, "Test started: " + displayName);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		current().log(Status.PASS, "Passed: " + result.getMethod().getMethodName());
	}

	@Override
	public void onTestFailure(ITestResult result) {
//		current().log(Status.FAIL, "Failed: " + result.getMethod().getMethodName());
		current().log(Status.FAIL, result.getThrowable());

		String testType = safeParam(result, "test-type");
		if ("ui".equalsIgnoreCase(testType)) {
			String path = DriverManager.getDriver().captureScreenshot(result.getMethod().getMethodName());
			if (path != null) {
				current().addScreenCaptureFromPath(path, "Screenshot on Failure");
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		current().log(Status.SKIP, "Skipped: " + result.getMethod().getMethodName());
	}

	// --------------- helpers ----------------
	private ExtentTest current() {
		return currentTest.get();
	}

	private static String safeParam(ITestContext ctx, String name) {
		try {
			return ctx.getCurrentXmlTest().getParameter(name);
		} catch (Exception e) {
			return null;
		}
	}

	private static String safeParam(ITestResult res, String name) {
		try {
			return res.getTestContext().getCurrentXmlTest().getParameter(name);
		} catch (Exception e) {
			return null;
		}
	}

	public static void logInfo(String msg) {
		if (currentTest.get() != null)
			currentTest.get().log(Status.INFO, msg);
	}

	public static void logWarning(String msg) {
		if (currentTest.get() != null)
			currentTest.get().log(Status.WARNING, msg);
	}

	public static void logResponse(String t, String body) {
		if (currentTest.get() != null)
			currentTest.get().log(Status.INFO, "<b>" + t + ":</b><pre>" + body + "</pre>");
	}
}
