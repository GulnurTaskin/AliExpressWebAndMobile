package pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // WebDriver üzerinden sayfa yüklendikten sonra reklam, popup, cookie kapatma düğmesini bul ve tıkla
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pop-close-btn")));
        closeButton.click();
        WebElement popupRefuse = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Ne pas autoriser']")));
        popupRefuse.click();
        WebElement cookiesAccept = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accepter les cookies']")));
        cookiesAccept.click();


    }





}