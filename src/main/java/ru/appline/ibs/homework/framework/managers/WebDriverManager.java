package ru.appline.ibs.homework.framework.managers;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.appline.ibs.homework.framework.utils.PropertyVars;

import static ru.appline.ibs.homework.framework.utils.PropertyVars.CHROME_DRIVER;
import static ru.appline.ibs.homework.framework.utils.PropertyVars.FIREFOX_DRIVER;

/**
 * Менеджер Веб-драйвера
 */
public class WebDriverManager {

    private static WebDriver driver;
    private static PropertyManager properties = PropertyManager.getPropertyManager();

    /**
     * Конструктор по паттерну Singleton
     *
     * @param PROP_VAR - константа, отвечающая за тип Веб-драйвера.
     * @see PropertyVars#FIREFOX_DRIVER
     * @see PropertyVars#CHROME_DRIVER
     */
    private WebDriverManager(PropertyVars PROP_VAR) {

        switch (PROP_VAR) {
            case FIREFOX_DRIVER:
                System.setProperty("webdriver.gecko.driver", properties.getProperty(FIREFOX_DRIVER.getPropertyVar()));
                driver = new FirefoxDriver();
                break;
            case CHROME_DRIVER:
                System.setProperty("webdriver.chrome.driver", properties.getProperty(CHROME_DRIVER.getPropertyVar()));
                driver = new ChromeDriver();
                break;
        }

    }

    /**
     * Метод инициализации Веб-драйвера
     *
     * @param PROP_VAR - передается в конструктор
     * @see WebDriverManager#WebDriverManager(PropertyVars PROP_VAR)
     */
    public static void initDriver(PropertyVars PROP_VAR) {

        if (driver == null) {
            new WebDriverManager(PROP_VAR);
        }

    }

    /**
     * Геттер Веб-драйвера
     *
     * @return WebDriver driver - Веб-драйвер Selenium
     */
    public static WebDriver getDriver() {

        if (driver == null) {
            Assertions.fail("Дравйвер не инициализирован. Инициализируйте драйвер командой \"initDriver\"");
        }
        return driver;

    }

    /**
     * Метод для остановки Веб-драйвера после отработки Тестов
     */
    public static void turnOff() {

        driver.quit();
        driver = null;

    }

}
