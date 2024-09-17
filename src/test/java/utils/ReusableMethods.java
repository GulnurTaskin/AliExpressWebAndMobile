package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Set;

public class ReusableMethods {
    private WebDriver driver;
    private WebDriverWait wait;

    public ReusableMethods(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Elementin tıklanabilir olmasını bekleyip tıklama işlemi
    public void waitAndClick(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
            System.out.println("Element başarıyla tıklandı: " + element);
        } catch (Exception e) {
            System.out.println("Element tıklanamadı: " + element + " Hata: " + e.getMessage());
        }
    }

    // Yeni açılan pencereye geçiş işlemi
    public void switchToNewWindow() {
        String currentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(currentWindow)) {
                driver.switchTo().window(window);
                System.out.println("Yeni pencereye geçildi: " + window);
                break;
            }
        }
    }

    // Orijinal pencereye geri dönme işlemi
    public void switchBackToOriginalWindow() {
        String originalWindow = driver.getWindowHandles().iterator().next();
        driver.switchTo().window(originalWindow);
        System.out.println("Orijinal pencereye geri dönüldü.");
    }

    // Belirli bir süre bekleme işlemi
    public void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Bekleme sırasında bir hata oluştu: " + e.getMessage());
        }
    }
    public void clickElement(WebElement element){
        // Click the found product
        if (element != null) {
            element.click();
        } else {
            System.out.println("No product found with the description: " + element);
        }
    }

}
