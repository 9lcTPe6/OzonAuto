package ru.appline.ibs.homework.framework.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.appline.ibs.homework.framework.managers.PageManager;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static ru.appline.ibs.homework.framework.managers.FrameworkLaunchManager.setImplicitlyWait;
import static ru.appline.ibs.homework.framework.managers.WebDriverManager.getDriver;
import static ru.appline.ibs.homework.framework.utils.PropertyVars.IMPLICITLY_WAIT;

/**
 * Базовый класс для всех страниц
 */
public class BasePage {

    /**
     * Создание и получение вспомогательных элементов
     */
    protected PageManager page = PageManager.getPageManager();
    protected Actions action = new Actions(getDriver());
    protected JavascriptExecutor js = (JavascriptExecutor) getDriver();
    protected WebDriverWait wait = new WebDriverWait(getDriver(), 20, 1000);

    /**
     * Хэдер, содержащий строку поиска и разделы меню
     */
    @FindBy(xpath = ".//header[@data-widget='header']")
    WebElement headMenu;

    /**
     * Значок количества товаров в корзине
     */
    @FindBy(xpath = ".//a[@data-widget='cart']/span[contains(@class, 'f')]")
    WebElement cartCount;

    /**
     * Конструктор базового класса страниц
     */
    public BasePage() {

        PageFactory.initElements(getDriver(), this);

    }

    /**
     * Переход в корзину с любой страницы
     *
     * @return CartPage cartPage - страница корзины
     */
    public CartPage goToCart() {

        WebElement cart = getDriver().findElement(By.xpath(".//a[@data-widget='cart']"));
        clickOnElement(cart);
        waitALittle(2500);
        return page.getCartPage();

    }

    /**
     * Метод для пролистывания страницы
     *
     * @param element - Веб-элемент, до которого необходимо прокрутить страницу
     */
    protected void scrollToElement(WebElement element) {

        js.executeScript("arguments[0].scrollIntoView({block: \"center\", behavior: \"smooth\"})", element);
        waitALittle(3000);

    }

    /**
     * Метод для клика по Веб-элементу
     *
     * @param element - Веб-элемент
     */
    protected void clickOnElement(WebElement element) {

        scrollToElement(element);
        action.moveToElement(element)
                .click()
                .build()
                .perform();

    }

    /**
     * Метод для заполнения формы
     *
     * @param element          - Элемент, содержащий ссылке на форму
     * @param filler           - строка, которую нужно ввести
     * @param needToPressEnter - параметр, определяющий, необходимо ли нажать "ENTER", после ввода строки
     */
    protected void clickAndFillElement(WebElement element, String filler, boolean needToPressEnter) {

        scrollToElement(element);
        if (needToPressEnter) {
            action.moveToElement(element)
                    .keyDown(element, Keys.CONTROL)
                    .sendKeys(element, "A")
                    .keyUp(element, Keys.CONTROL)
                    .sendKeys(element, filler)
                    .sendKeys(element, Keys.RETURN)
                    .build()
                    .perform();
        } else {
            action.moveToElement(element)
                    .keyDown(element, Keys.CONTROL)
                    .sendKeys(element, "A")
                    .keyUp(element, Keys.CONTROL)
                    .sendKeys(element, filler)
                    .build()
                    .perform();
        }
    }

    /**
     * Метод для преобразования неформатированной строки в Веб-элемент
     *
     * @param parentElement - родительский Веб-элемент, в котором содержится необходимый Веб-элемент
     * @param grandElement  - неформатированная строка xPath
     * @param subElement    - строка для форматирования
     * @return WebElement element - возвращает преобразованный Веб-элемент
     */
    protected WebElement convertIntoWebElement(WebElement parentElement, String grandElement, String subElement) {

        String target = String.format(grandElement, subElement);
        return parentElement.findElement(By.xpath(target));

    }

    /**
     * Метод для получения заголовка страницы
     *
     * @return String pageTitle
     */
    protected String getPageTitle() {

        return getDriver().getTitle();

    }

    /**
     * Метод для ожидания. Используется, чтобы значения успели поменяться и записаться в соотвествующие переменные
     */
    protected void waitALittle(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для проверки видимости элемента
     *
     * @param by - locator передаваемого элемента
     * @return boolean isVisible - true - элемент найден, false - элемент не найден
     */
    protected boolean isVisible(By by) {
        setImplicitlyWait(1, false);
        try {
            getDriver().findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            setImplicitlyWait(0, true);
        }
    }

}
