package tests;

import org.example.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.EventsPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventsPageTest extends BaseTest {

    @Test
    public void checkIfTypeActivityFilterWorking(){
        String onlineStatus = "Online event";
        EventsPage eventsPage = new EventsPage(driver, wait);

        eventsPage.open();
        pageUtils.closeCookiesIfPresent();
        pageUtils.closeGooglePickerContainer();
        eventsPage.getTypeActivityButton().click();
        eventsPage.getOnlineActivityButton().click();
        eventsPage.getActivities().get(2).click();
        pageUtils.closeCookiesIfPresent();
        pageUtils.closeGooglePickerContainer();
        pageUtils.changeLanguage(Language.ENGLISH);
        pageUtils.waitForText(eventsPage.getActivityType(), onlineStatus);
        Assertions.assertEquals(onlineStatus, eventsPage.getActivityType().getText());

        eventsPage.open();
        pageUtils.closeCookiesIfPresent();
        pageUtils.closeGooglePickerContainer();
        eventsPage.getTypeActivityButton().click();
        eventsPage.getOfflineActivityButton().click();
        eventsPage.getActivities().get(2).click();
        pageUtils.closeCookiesIfPresent();
        pageUtils.closeGooglePickerContainer();
        pageUtils.changeLanguage(Language.ENGLISH);
        assertTrue(eventsPage.getActivityLocation().isDisplayed());
    }

    @Test
    void checkIfThemeOfActivityFilterWorking() {
        EventsPage eventsPage = new EventsPage(driver, wait);
        eventsPage.open();
        pageUtils.closeCookiesIfPresent();
        pageUtils.closeGooglePickerContainer();

        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@aria-label=\"Scroll right\"]")
        ));
        element.click();

        WebElement ninth = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@data-testid=\"category-604\"]")
        ));
        ninth.click();
        eventsPage.getActivities().getFirst().click();
        element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//main/div[2]/div")));
        assertTrue(element.isDisplayed());
    }

    @Test
    void userCanNotStartNewGroupSignIn() {
        EventsPage eventsPage = new EventsPage(driver, wait);
        eventsPage.open();
        pageUtils.closeGooglePickerContainer();

        WebElement element = driver.findElement(By.xpath("//div[4]/div/a"));
        new Actions(driver).moveToElement(element).perform();
        element.click();

        Assertions.assertTrue(driver.getCurrentUrl().contains("/login/"));

    }

}
