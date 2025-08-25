package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PageUtils;

import java.time.Duration;

public abstract class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected PageUtils pageUtils;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "firefox");

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.addArguments("--disable-popup-blocking");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
            default:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                firefoxOptions.addPreference("dom.push.enabled", false);
                driver = new FirefoxDriver(firefoxOptions);
                break;
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        pageUtils = new PageUtils(driver, wait);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
