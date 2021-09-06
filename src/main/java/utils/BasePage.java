package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.testng.Assert.assertTrue;

public class BasePage {

    protected Logger logger = LogManager.getLogger(getClass().getName());
    protected WebDriver driver;
    private WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 20);
    }

    protected WebElement element(By loc) {
        wait.until(ExpectedConditions.presenceOfElementLocated(loc));
        return driver.findElement(loc);

    }

    protected WebElement elementCanClickable(By loc) {
        try
        {
            wait.until(ExpectedConditions.elementToBeClickable(loc));
            return driver.findElement(loc);
        }
        catch(TimeoutException e)
        {
            logger.error("Element bulunamadı");
            return driver.findElement(loc);
        }
    }

    protected void titleVisible(String title) {
        try
        {
            wait.until(ExpectedConditions.titleContains(title));
        }
        catch(TimeoutException e)
        {
            assertTrue(driver.getTitle().contains(title));
        }
    }

    protected void sendKeys(By loc, String value) {
        element(loc).sendKeys(value);
    }

    protected void selectElement(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }

    protected void hoverElement(WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(element).perform();
    }

    protected void sleepTime(long waitMilisecond)
    {
        try{
            Thread.sleep(waitMilisecond);
        }
        catch(InterruptedException ie){
            logger.info("Bekleme uygulanırken bir hata oluştu");
        }
    }
}