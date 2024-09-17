package stepDefinitions;

import io.cucumber.java.en.*;
import pages.mobile.HomePageMobile;
import pages.mobile.LoginPageMobile;
import driver.DriverManager;

import java.net.MalformedURLException;

public class MobileStepDefinitions {
    private HomePageMobile homePage;
    private LoginPageMobile loginPage;

    @Given("I am on the AliExpress mobile app")
    public void iAmOnTheAliExpressMobileApp() throws MalformedURLException {
        homePage = new HomePageMobile(DriverManager.getMobileDriver());
    }



    // Diğer mobil adımlar...
}