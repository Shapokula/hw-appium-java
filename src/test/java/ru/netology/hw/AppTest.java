package ru.netology.hw;

import io.appium.java_client.AppiumDriver;

import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static org.junit.jupiter.api.TestInstance.Lifecycle;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

@TestInstance(Lifecycle.PER_CLASS)
public class AppTest {

    private AppiumDriver driver;
    private MainScreen mainScreen;

    private URL getUrl() {
        try {
            return new URL("http://127.0.0.1:4723");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @BeforeAll
    public void createDriver() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(DEVICE_NAME, "Pixel 8");
        desiredCapabilities.setCapability(APP_PACKAGE, "ru.netology.testing.uiautomator");
        desiredCapabilities.setCapability(APP_ACTIVITY, "ru.netology.testing.uiautomator.MainActivity");
        desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
        driver = new AppiumDriver(this.getUrl(), desiredCapabilities);

    }

    @BeforeEach
    public void createMainScreen() {
        mainScreen = new MainScreen(driver);
        mainScreen.textToBeChanged.isDisplayed();
        mainScreen.buttonChange.isDisplayed();
        mainScreen.userInput.isDisplayed();
    }

    @Test
    public void changeToEmptyText() {
        var initialText = mainScreen.textToBeChanged.getText();

        mainScreen.userInput.sendKeys("");
        mainScreen.buttonChange.click();
        var result1 = mainScreen.textToBeChanged.getText();
        Assertions.assertEquals(initialText, result1);

        mainScreen.userInput.sendKeys("              ");
        mainScreen.buttonChange.click();
        var result2 = mainScreen.textToBeChanged.getText();
        Assertions.assertEquals(initialText, result2);
    }

    @Test
    public void activityScreen() {
        ActivityScreen activityScreen = new ActivityScreen(driver);

        var textToInput = "Hello";
        mainScreen.userInput.sendKeys(textToInput);
        mainScreen.buttonActivity.click();
        activityScreen.text.isDisplayed();
        Assertions.assertEquals(textToInput, activityScreen.text.getText());
    }

    @AfterAll
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}