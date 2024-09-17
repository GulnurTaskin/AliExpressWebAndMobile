package hooks;

import driver.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class Hooks {
    @Before
    public void setUp() {

        // WebDriver instance'ını başlat
        WebDriver driver = DriverManager.getWebDriver();

        // Tarayıcı penceresini tam ekran yap (isteğe bağlı)
        driver.manage().window().maximize();

        // Test URL'sine git (configuration.properties dosyasından)
        String url = ConfigReader.getProperty("webUrl");
        driver.get(url);

        // Diğer gerekli ayarlamalar burada yapılabilir (çerezler, giriş, vb.)
        System.out.println("Test öncesi hazırlıklar tamamlandı.");
        // Test öncesi hazırlıklar

    }

    @After
    public void tearDown() {
        DriverManager.quitDrivers();
    }
}