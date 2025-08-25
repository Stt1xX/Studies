package tests;

import org.example.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.MainPage;

public class MainPageTest extends BaseTest {

    @Test
    public void checkIfHeaderIsDisplayed() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        Assertions.assertTrue(mainPage.getHeader().isDisplayed());
    }

    @Test
    public void checkIfLogoIsDisplayed() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        Assertions.assertTrue(mainPage.getLogo().isDisplayed());
    }

    @Test
    public void checkIfFooterIsDisplayed() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        Assertions.assertTrue(mainPage.getFooter().isDisplayed());
    }

    @Test
    public void checkIfChangingLanguageWorks(){
        String rusLang = "Платформа для людей, где с общих интересов начинается дружба";
        String engLang = "The people platform—Where interests become friendships";
        MainPage mainPage = new MainPage(driver);
        mainPage.open();

        pageUtils.closeCookiesIfPresent();
        pageUtils.closeGooglePickerContainer();

        pageUtils.changeLanguage(Language.RUSSIAN);
        pageUtils.waitForText(mainPage.getHeader(), rusLang);
        Assertions.assertEquals(rusLang, mainPage.getHeader().getText());

        pageUtils.changeLanguage(Language.ENGLISH);
        pageUtils.waitForText(mainPage.getHeader(), engLang);
        Assertions.assertEquals(engLang, mainPage.getHeader().getText());
    }
}
