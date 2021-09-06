package testcasefunction;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utils.BasePage;
import utils.Constants;

public class LoginFunction extends BasePage {

    private final By inpUsername = By.name("username");
    private final By inpPassword = By.name("password");
    private final By btnSubmit = By.xpath("//button[@type='submit']");
    private final By imgProfile = By.xpath("(//img[contains(@alt,'" + Constants.AccountUsername + "')])[1]");

    public LoginFunction(WebDriver driver) {
        super(driver);
    }

    public void loginfunction() {
        sendKeys(inpUsername, Constants.AccountUsername);
        sendKeys(inpPassword, Constants.AccountPassword);
        element(btnSubmit).click();
        logger.info(Constants.AccountUsername + " kullanıcısına giriş yapılıyor");
        Assert.assertTrue(element(imgProfile).isDisplayed());
    }
}
