package ru.appline.ibs.homework.framework.utils;

/**
 * Класс с константами для получения значений из файла .properties
 */
public enum PropertyVars {

    TEST_URL ("site.url"),
    BROWSER_TYPE ("type.browser"),
    CHROME_DRIVER ("path.chrome.driver"),
    FIREFOX_DRIVER ("path.gecko.driver"),
    IMPLICITLY_WAIT ("implicitly.wait"),
    PAGELOAD_WAIT ("page.load.timeout"),
    SCRIPT_WAIT ("set.script.timeout");

    private final String PROP_VAR;

    PropertyVars(String PROP_VAR) {

        this.PROP_VAR = PROP_VAR;

    }

    public String getPropertyVar() {

        return this.PROP_VAR;

    }

}
