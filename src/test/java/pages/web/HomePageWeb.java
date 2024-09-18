package pages.web;

import driver.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.base.BasePage;

public class HomePageWeb extends BasePage {
    private By searchInput = By.id("search-key");
    private By accountButton = By.className("user-account-info");

    public HomePageWeb(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "search-words")
    public WebElement searchBox;

    // "Plus" butonunun locator'ı
    public static By plusButton = By.cssSelector("span.pc2023-header-tab--moreText--o8dQidV");

    // Categories
    @FindBy(xpath = "//*[@*='Categoey--categoryTitle--_3bKGRN']")
    public WebElement categories;

    // kategoriler icin dinamik locate alma methodu
    public WebElement getCategoryElement(String categoryName) {
        WebDriver driver = DriverManager.getWebDriver();
        String dynamicXpath = "//div[text()='" + categoryName + "']";
        return driver.findElement(By.xpath(dynamicXpath));
    }


    // secilen kategorinin acilan sayfadaki sayfa basligi
    @FindBy(xpath = "//*[@class='component--lv3CategoryTitle--3NEC_gC']")
    public WebElement categoryPageTitle;

    @FindBy(xpath = "//*[@*='ship-to--text--3H_PaoC']")
    public WebElement langueButton;

    @FindBy(xpath = "//*[text()='Enregistrer']")
    public WebElement enregistrer;

    @FindBy(xpath = "//div[text()='Ne pas autoriser']")
    public WebElement popupRefuse;

    @FindBy(xpath = "//button[text()='Accepter les cookies']")
    public WebElement cookiesAccept;
}