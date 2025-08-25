package pages;

import org.example.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class EventsPage extends BasePage {

    public EventsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Override
    public void open() {
        driver.get(Page.EVENTS_PAGE.getUrl());
    }

    public WebElement getTypeActivityButton() {
        return driver.findElement(By.xpath("//button[@data-testid=\"RevampedFilterButtonVenue\"]"));
    }

    public WebElement getOnlineActivityButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-event-label=\"event-type-online-option\"]"))
        );
    }

    public WebElement getOfflineActivityButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@data-event-label=\"event-type-in-person-option\"]"))
        );
    }

    public List<WebElement> getActivities(){
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//div[@data-element-name=\"categoryResults-eventCard\"]"))
        );
    }

    public WebElement getActivityType(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@data-testid=\"venue-name-value\"]"))
        );
    }

    public WebElement getActivityLocation(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[@data-event-label=\"event-location\"]"))
        );
    }
}
