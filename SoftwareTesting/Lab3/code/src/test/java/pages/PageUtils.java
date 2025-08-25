package pages;

import org.example.Language;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Map;

public class PageUtils {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By trustAcceptBtn = By.xpath("//button[@id=\"onetrust-accept-btn-handler\"]");
    private final By googleLoginCloseBtn = By.xpath("//div[@id=\"close\"]");
    private final Map<Language, By> languageLocators = Map.of(
            Language.ENGLISH, By.xpath("//label[@for='en-US']"),
            Language.RUSSIAN, By.xpath("//label[@for='ru-RU']")
    );

    public PageUtils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void changeLanguage(Language lang) {
        getLanguageChanger().click();

        WebElement span = wait.until(
                ExpectedConditions.visibilityOfElementLocated(languageLocators.get(lang))
        );
        span.click();
        getButtonForSaveLanguage().click();
    }

    public void waitForText(WebElement element, String expectedText) {
        try{
            wait.until(ExpectedConditions.textToBePresentInElement(element, expectedText));
        } catch(Exception e) {
            // skip...
        }

    }


    public void closeCookiesIfPresent() {
        try {
            WebElement closeBtn = wait.until(
                    ExpectedConditions.presenceOfElementLocated(trustAcceptBtn)
            );
            if (closeBtn.isDisplayed()) {
                closeBtn.click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(trustAcceptBtn));
            }
        } catch (Exception ignored) {
            // Where are you bloody banner???
        }
    }

    public void closeGooglePickerContainer(){
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(
                    By.xpath("//iframe[contains(@src,'accounts.google.com/gsi/iframe/select')]"))
            );

            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(googleLoginCloseBtn));
            button.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(googleLoginCloseBtn));

        } catch (TimeoutException e) {
            // skip...
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    private WebElement getLanguageChanger() {
        return driver.findElement(By.xpath("//button[@data-event-label=\"Language selection\"]"));
    }

    private WebElement getButtonForSaveLanguage() {
        return driver.findElement(By.xpath("//button[@data-event-label=\"save-language-modal\"]"));
    }
}
