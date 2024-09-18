package pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ReusableMethods;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ReusableMethods reusableMethods;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.reusableMethods = new ReusableMethods(driver);

        // Sayfa yüklendikten sonra popup, reklam ve çerez kapatma işlemleri
        try {
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".pop-close-btn")));
            closeButton.click();
            System.out.println("Reklam pop-up başarıyla kapatıldı.");
        } catch (Exception e) {
            System.out.println("Reklam pop-up bulunamadı veya zaten kapalı.");
        }

        try {
            WebElement popupRefuse = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Ne pas autoriser']")));
            popupRefuse.click();
            System.out.println("Pop-up başarıyla reddedildi.");
        } catch (Exception e) {
            System.out.println("Pop-up bulunamadı veya zaten kapalı.");
        }

        try {
            WebElement cookiesAccept = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accepter les cookies']")));
            cookiesAccept.click();
            System.out.println("Çerezler başarıyla kabul edildi.");
        } catch (Exception e) {
            System.out.println("Çerez kabul butonu bulunamadı veya zaten kabul edilmiş.");
        }
    }
}
