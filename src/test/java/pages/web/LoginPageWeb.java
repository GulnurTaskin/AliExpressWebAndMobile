package pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.base.BasePage;

public class LoginPageWeb extends BasePage {
    private By usernameInput = By.id("fm-login-id");
    private By passwordInput = By.id("fm-login-password");
    private By loginButton = By.className("fm-button");

    public LoginPageWeb(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
       // enterText(usernameInput, username);
    }

    public void enterPassword(String password) {
       // enterText(passwordInput, password);
    }

}