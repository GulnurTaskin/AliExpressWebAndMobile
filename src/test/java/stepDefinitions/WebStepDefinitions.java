package stepDefinitions;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.web.HomePageWeb;
import pages.web.LoginPageWeb;
import driver.DriverManager;
import utils.ConfigReader;
import java.time.Duration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.ReusableMethods;

public class WebStepDefinitions {
    private WebDriver driver = DriverManager.getWebDriver();
    private ReusableMethods reusableMethods = new ReusableMethods(driver); // ReusableMethods nesnesini oluştur
    private HomePageWeb homePage;
    private LoginPageWeb loginPage;

    @Given("Je suis sur la plateforme AliExpress")
    public void je_suis_sur_la_plateforme_ali_express() throws InterruptedException {
        WebDriver driver = DriverManager.getWebDriver();

        // Başlangıçta homePage'in null olmadığından emin ol
        if (homePage == null) {
            homePage = new HomePageWeb(driver); // HomePage'i başlat
        }

        // Bekleme işlemi
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Ana sayfa yüklendiğini kontrol et
        try {
            WebElement plusButton = wait.until(ExpectedConditions.visibilityOfElementLocated(HomePageWeb.plusButton));
            Assert.assertTrue("Ana sayfa doğrulanamadı, 'Plus' butonu görünmüyor!", plusButton.isDisplayed());
            System.out.println("Ana sayfa başarıyla yüklendi.");
        } catch (NoSuchElementException | TimeoutException e) {
            Assert.fail("Ana sayfa yüklü değil veya 'Plus' butonu bulunamadı.");
        }

        // Pop-up ve çerez işlemleri
        try {
            if (homePage.popupRefuse != null) {
                WebElement popupRefuse = wait.until(ExpectedConditions.visibilityOf(homePage.popupRefuse));
                reusableMethods.waitAndClick(popupRefuse);
                System.out.println("Pop-up başarıyla kapatıldı.");
            }
        } catch (TimeoutException e) {
            System.out.println("Pop-up bulunamadı veya zaten kapalı.");
        }

        try {
            if (homePage.cookiesAccept != null) {
                WebElement cookiesAccept = wait.until(ExpectedConditions.visibilityOf(homePage.cookiesAccept));
                reusableMethods.waitAndClick(cookiesAccept);
                System.out.println("Çerezler başarıyla kabul edildi.");
            }
        } catch (TimeoutException e) {
            System.out.println("Çerez kabul butonu bulunamadı veya zaten kabul edilmiş.");
        }
    }

    @When("Je vérifie que l'URL est correcte")
    public void je_verifie_que_l_url_est_correcte() {
        // Beklenen URL'yi configuration dosyasından al
        String expectedUrl = ConfigReader.getProperty("webUrl");
        String actualUrl = DriverManager.getWebDriver().getCurrentUrl();

        // Beklenen domain kısmını ayır
        String expectedDomain = expectedUrl.replace("https://", "").replace("http://", "").replace("www.", "");

        // Bilgilendirme amaçlı yazdır
        System.out.println("Beklenen domain: " + expectedDomain);
        System.out.println("Gerçek URL: " + actualUrl);

        // URL'nin doğru olup olmadığını kontrol et
        Assert.assertTrue("URL doğrulanamadı: Beklenen -> " + expectedDomain + ", Gerçek -> " + actualUrl,
                actualUrl.contains(expectedDomain));
    }

    // Anasayfada olduğumuzu doğruluyoruz
    @Then("Je suis sur la page d'acceuil d'AliExpress")
    public void je_suis_sur_la_page_d_accueil_d_ali_express() {

        // WebDriver instance'ını alıyoruz
        WebDriver driver = DriverManager.getWebDriver();

        // 1. "Plus" butonunu kontrol et
        try {
            // "Plus" butonunu buluyoruz
            WebElement plusButton = driver.findElement(HomePageWeb.plusButton);

            // Eğer buton görünürse, test başarılı olur
            Assert.assertTrue("Ana sayfa doğrulanamadı, 'Plus' butonu görünmüyor!", plusButton.isDisplayed());
            System.out.println("Ana sayfa başarıyla yüklendi.");
        } catch (NoSuchElementException e) {
            // Eğer buton bulunamazsa, test başarısız olur
            Assert.fail("'Plus' butonu bulunamadı, ana sayfa yüklü değil.");
        }
    }

    // Tüm Kategoriler butonunun üzerine geliriz
    @Then("Je suis sur la Toutes les Categories")
    public void je_suis_sur_la_toutes_les_categories() {
        WebDriver driver = DriverManager.getWebDriver();

        // homePage'i başlat
        if (homePage == null) {
            homePage = new HomePageWeb(driver);
        }

        WebElement categoryButton = driver.findElement(By.xpath("//*[@*='Categoey--categoryTitle--_3bKGRN']"));
        categoryButton.click();
        System.out.println("L'utilisateur est sur la Toutes Les Catégories" );
    }

    // İstediğimiz kategoriye tıklıyoruz
    @When("Je clique sur la categorie {string}")
    public void je_clique_sur_la_categorie(String categoryName) {
        if (homePage == null) {
            homePage = new HomePageWeb(DriverManager.getWebDriver());
        }

        // categoryName parametresi ile ilgili kategori elementini alıyoruz
        WebElement categoryElement = homePage.getCategoryElement(categoryName);

        // Kategoriyi bulup, reusableMethods yardımıyla tıklıyoruz
        reusableMethods.waitAndClick(categoryElement);
        System.out.println("L'utilisateur a cliqué sur la catégorie: " + categoryName);
    }


    // Seçilen kategori sayfasına yönlendirildiğimizi doğruluyoruz
    @Then("Je devrais être redirigé vers la page de la catégorie {string}")
    public void je_devrais_etre_redirige_vers_la_page_de_la_categorie(String expectedCategory) throws InterruptedException {
        WebDriver driver = DriverManager.getWebDriver();

        // homePage null ise başlat
        if (homePage == null) {
            homePage = new HomePageWeb(driver);
        }

        // Yeni sayfanın yüklenmesini bekle ve yeni pencereye geçiş yap
        reusableMethods.switchToNewWindow();

        // Biraz bekle (isteğe bağlı, geçiş sonrası yüklenme süresi için)
        reusableMethods.wait(2);

        // Bekleme süresini artırarak sayfa başlığını bekle
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Yeni pencereye geçiş kontrolü ve sayfa başlığı görünürlüğü
        try {
            // Sayfa başlığının görünür olmasını bekliyoruz
            wait.until(ExpectedConditions.visibilityOf(homePage.categoryPageTitle));

            // Sayfa başlığını alıyoruz
            String actualCategoryTitle = homePage.categoryPageTitle.getText();

            // Sayfa başlığının beklenen kategori ile eşleşip eşleşmediğini kontrol ediyoruz
            Assert.assertTrue("L'utilisateur n'est pas sur la bonne catégorie: " +
                            "Expected: " + expectedCategory + ", but got: " + actualCategoryTitle,
                    actualCategoryTitle.equalsIgnoreCase(expectedCategory));

            System.out.println("L'utilisateur est sur la page de la catégorie: " + actualCategoryTitle);
        } catch (TimeoutException e) {
            // Sayfa başlığı bulunamadığında veya sayfa yüklenmediğinde hata mesajı veriyoruz
            System.out.println("TimeoutException: Kategori sayfası başlığı bulunamadı.");
            e.printStackTrace();
            Assert.fail("Kategori sayfası başlığı bulunamadı veya sayfa yüklenmedi.");
        } catch (NoSuchElementException e) {
            // Eğer element bulunamazsa, hata mesajı veriyoruz
            System.out.println("NoSuchElementException: Kategori sayfası başlığı bulunamadı.");
            e.printStackTrace();
            Assert.fail("Kategori sayfası başlığı bulunamadı.");
        }
    }

}
