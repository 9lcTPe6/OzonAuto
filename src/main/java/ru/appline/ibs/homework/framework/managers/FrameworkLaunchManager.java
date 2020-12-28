package ru.appline.ibs.homework.framework.managers;

import ru.appline.ibs.homework.framework.utils.PropertyVars;

import java.util.concurrent.TimeUnit;

import static ru.appline.ibs.homework.framework.managers.PageManager.cleanPages;
import static ru.appline.ibs.homework.framework.managers.WebDriverManager.*;
import static ru.appline.ibs.homework.framework.utils.PropertyVars.*;

/**
 * Менеджер, отвечающий за запуск и работу Фреймворка
 */
public class FrameworkLaunchManager {

    private static PropertyManager property = PropertyManager.getPropertyManager();

    /**
     * Лаунчер Фреймворка
     *
     * @param PROP_VAR - переменная, передающаяся в метод initDriver()
     * @see PropertyVars
     * @see WebDriverManager#initDriver(PropertyVars PROP_VAR)
     */
    public static void launchFramework(PropertyVars PROP_VAR) {

        initDriver(PROP_VAR);
        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Long.parseLong(property.getProperty(IMPLICITLY_WAIT.getPropertyVar())), TimeUnit.SECONDS);
        getDriver().manage().timeouts().pageLoadTimeout(Long.parseLong(property.getProperty(PAGELOAD_WAIT.getPropertyVar())), TimeUnit.SECONDS);
        getDriver().manage().timeouts().setScriptTimeout(Long.parseLong(property.getProperty(SCRIPT_WAIT.getPropertyVar())), TimeUnit.SECONDS);
        getDriver().get(property.getProperty(TEST_URL.getPropertyVar()));

    }

    /**
     * Остановка работы Фреймворка
     */
    public static void stopFramework() {

        cleanPages();
        turnOff();

    }

    /**
     * Метод для установки неявного ожидания
     *
     * @param seconds         - время ожидания в секундах
     * @param returnToDefault - true - для возврата значения по умолчанию, false - для установки передаваемого времени seconds
     */
    public static void setImplicitlyWait(int seconds, boolean returnToDefault) {

        if (returnToDefault) {
            getDriver().manage().timeouts().implicitlyWait(Long.parseLong(property.getProperty(IMPLICITLY_WAIT.getPropertyVar())), TimeUnit.SECONDS);
        } else {
            getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        }

    }

}
