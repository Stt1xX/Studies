package pages;

import org.example.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MainPage extends BasePage {

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void open() {
        driver.get(Page.BASE_PAGE.getUrl());
    }

    public WebElement getHeader() {
        return driver.findElement(By.xpath("//h1[@data-testid=\"search-intro-title\"]"));
    }

    public WebElement getLogo() {
        return driver.findElement(By.xpath("//a[@data-event-label=\"Meetup logo\"]"));
    }

    public WebElement getFooter() {
        return driver.findElement(By.xpath("//nav[text()='Ваш аккаунт']"));
    }
}
