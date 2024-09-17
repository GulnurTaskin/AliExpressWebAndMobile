package pages.mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.base.BasePage;
import org.openqa.selenium.By;

public class HomePageMobile extends BasePage {

    @FindBy(id="search-words")
    public WebElement searchBar;

    public HomePageMobile(AppiumDriver driver) {
        super(driver);
    }



}