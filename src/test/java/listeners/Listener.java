package listeners;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.MDC;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utils.BaseTest;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Listener extends BaseTest implements ITestListener {

    public void onTestStart(ITestResult iTestResult) {
        driver.get(baseUrl);
    }

    public void onTestSuccess(ITestResult iTestResult) {
        System.out.println("Success");
    }

    public void onTestFailure(ITestResult iTestResult) {
        logger.error(iTestResult.getThrowable());
        logger.error("Fail");
        takeScreenShot();
        MDC.put("fail", iTestResult.getThrowable());
    }

    public void takeScreenShot()
    {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String projectPath = System.getProperty("user.dir");
        String filepath = (projectPath + "\\ScreenShots\\screenshot_" + System.currentTimeMillis() + ".png");
        try {
            FileUtils.copyFile(screenshot, new File(filepath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void onTestSkipped(ITestResult iTestResult) {
        System.out.println("Skip");
        logger.warn("Skip");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    public void onStart(ITestContext iTestResult) {
        String projectPath = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", projectPath + "\\driver\\chromedriver.exe");
        driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", Arrays.asList("disable-popup-blocking"));
        driver.manage().window().maximize();
        logger.info("Browser opened ");
    }

    public void onFinish(ITestContext iTestResult) {
        driver.quit();
        logger.info("Closed browser");
    }
}