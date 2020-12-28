package ru.appline.ibs.homework.framework.managers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Менеджер настроек
 */
public class PropertyManager {

    private final Properties PROPERTIES = new Properties();
    private static PropertyManager instance = null;

    /**
     * Конструктор по паттерну Singleton
     */
    private PropertyManager() {

        try {
            PROPERTIES.load(new FileInputStream(
                    "src/main/resources/" +
                            System.getProperty("variables", "app") + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Геттер для PropertyManager
     *
     * @return PropertyManager instance - объект PropertyManager
     */
    public static PropertyManager getPropertyManager() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    /**
     * Метод для получения переменной из файла настроек
     *
     * @param key - переменная, соответсвующая определенной строке из файла настроек
     * @return String PROPERTIES - константа настроек
     * @see ru.appline.ibs.homework.framework.utils.PropertyVars
     */
    public String getProperty(String key) {

        return PROPERTIES.getProperty(key);

    }

    /**
     * Перегрузка метода getProperty
     *
     * @param key          - переменная, соответсвующая определенной строке из файла настроек
     * @param defaultValue - переменная, возвращаемая по умолчанию, если key не найден
     * @return String PROPERTIES - константа настроек
     * @see PropertyManager#getProperty(String key)
     */
    public String getProperty(String key, String defaultValue) {

        return PROPERTIES.getProperty(key, defaultValue);

    }

}
