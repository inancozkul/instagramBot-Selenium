package testCase;

import listeners.Listener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import testcasefunction.HomePageFunction;
import testcasefunction.LoginFunction;
import utils.BaseTest;

import java.io.IOException;

@Listeners({Listener.class})
public class HomePageTest extends BaseTest {

    @Test(priority = 1)
    public void letTheGameBegin() throws IOException {

        LoginFunction login = new LoginFunction(driver);
        login.loginfunction();
        HomePageFunction homepage = new HomePageFunction(driver);
        homepage.letTheGameBegin();
    }

}
