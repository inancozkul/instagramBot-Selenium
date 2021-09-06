package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected static WebDriver driver;
    protected String baseUrl = "https://www.instagram.com/accounts/login/?source=auth_switcher";
    protected Logger logger = LogManager.getLogger(getClass().getName());
}
