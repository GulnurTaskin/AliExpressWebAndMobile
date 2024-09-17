package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import config.ConfigReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static ThreadLocal<AppiumDriver> mobileDriver = new ThreadLocal<>();

    // WebDriver'ı başlatan ve döndüren metot
    public static WebDriver getWebDriver() {
        if (webDriver.get() == null) {
            String browser = ConfigReader.getProperty("browser");  // config'den tarayıcı bilgisi alınıyor
            webDriver.set(getWebDriver(browser));  // Tarayıcıya göre driver'ı başlatıyor
        }
        return webDriver.get();
    }

    // WebDriver'ı tarayıcıya göre başlatan ve döndüren metot (Chrome, Firefox, Edge eklemeleriyle)
    public static WebDriver getWebDriver(String browser) {
        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "chrome":
                // Chrome için özel ayarlar ve opsiyonlar ekleniyor
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-search-engine-choice-screen");
                chromeOptions.addArguments("--no-first-run"); // Tarayıcı ilk kurulum adımlarını geç
                chromeOptions.addArguments("--start-maximized"); // Tarayıcıyı maksimum boyutta aç
                chromeOptions.addArguments("--disable-default-apps"); // Varsayılan uygulamaları devre dışı bırak
                chromeOptions.addArguments("--no-default-browser-check"); // Varsayılan tarayıcı kontrolünü geç
                driver = new ChromeDriver(chromeOptions); // ChromeDriver'ı opsiyonlarla başlat
                break;

            case "firefox":
                // Firefox için özel ayarlar ekleniyor
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                driver = new FirefoxDriver(firefoxOptions); // Firefox tarayıcı başlatılıyor
                break;

            case "edge":
                // Edge için özel ayarlar ekleniyor
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--start-maximized");
                driver = new EdgeDriver(edgeOptions); // Edge tarayıcı başlatılıyor
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // Başlatılan tarayıcı ile URL'yi açıyoruz
        driver.get(ConfigReader.getProperty("webUrl"));
        return driver;
    }

    // Mobil driver'ı başlatan ve döndüren metot
    public static AppiumDriver getMobileDriver() throws MalformedURLException {
        if (mobileDriver.get() == null) {
            String platform = ConfigReader.getProperty("mobile.platform");  // config'den platform bilgisi alınıyor
            String appiumUrl = ConfigReader.getProperty("appium.url");      // Appium server URL'si

            if ("android".equalsIgnoreCase(platform)) {
                mobileDriver.set(new AndroidDriver(new URL(appiumUrl), (Capabilities) null));
            } else if ("ios".equalsIgnoreCase(platform)) {
                mobileDriver.set(new IOSDriver(new URL(appiumUrl), null));
            } else {
                throw new RuntimeException("Unsupported mobile platform: " + platform);
            }
        }
        return mobileDriver.get();
    }

    // Tüm driver'ları kapatan metot
    public static void quitDrivers() {
        // WebDriver'ı kapatma
        if (webDriver.get() != null) {
            webDriver.get().quit();
            webDriver.remove();
        }
        // Mobil driver'ı kapatma
        if (mobileDriver.get() != null) {
            mobileDriver.get().quit();
            mobileDriver.remove();
        }
    }
}
