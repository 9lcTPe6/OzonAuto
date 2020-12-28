package ru.appline.ibs.homework.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static ru.appline.ibs.homework.framework.managers.WebDriverManager.getDriver;

/**
 * Страница поиска
 */
public class SearchPage extends BasePage {

    /**
     * Веб-элемент бокового меню
     */
    @FindBy(xpath = ".//aside")
    WebElement filters;

    /**
     * Лист Веб-элементов с товарами
     */
    @FindBy(xpath = ".//div[@class='a0t8 a0u']")
    List<WebElement> productElements;

    /**
     * Лист, содержащий названия добавленных в корзину товаров
     */
    private static List<String> addedProducts = new ArrayList<>();

    /**
     * Переменная, используемая для перехода на следующую страницу
     */
    private static Integer pageNumber = 2;

    /**
     * Константы с xPath для форматирования
     */
    private final String checkboxes = ".//div[@value='%s']//div[@class='ui-at4']";
    private final String squareCheckboxes = ".//span[contains(text(), '%s')]/../../div[@class='ui-at4']";
    private final String listOfSomething = ".//div[@class='b7n1' and contains(., '%s')]/..//span[@class='cy4' and contains(., 'Посмотреть все')]";
    private final String brandsList = ".//a[@class='c0y6 c0z']//span[contains(., '%s')]/../..//input";
    private final String productTitle = ".//a[@class='a2g0 tile-hover-target']";

    /**
     * Получение списка добавленных товаров
     *
     * @return List<String> addedProducts - лист, содержащий названия добавленных в корзину товаров
     */
    public static List<String> getAddedProducts() {

        return addedProducts;

    }

    /**
     * Очистка коллекции от элементов
     *
     * @param list - список, который нужно очистить
     * @param <E>  - тип элементов, содержащихся в списке
     */
    static <E> void clearList(List<E> list) {

        list.clear();

    }

    /**
     * Проверка страницы поиска
     *
     * @return SearchPage page.getSearchPage - возвращает страницу поиска
     */
    public SearchPage checkSearchPage() {

        String checker = ".//div[@data-widget='fulltextResultsHeader']/div[contains(.,'найден')]";
        WebElement checkerElement = getDriver().findElement(By.xpath(checker));
        Assertions.assertTrue(checkerElement.getText().contains("По запросу"), "Данная страница не является страницей поиска");
        return this;

    }

    /**
     * Установка ограничения цены для отображаемых товаров
     *
     * @param priceBorder1 - нижняя граница диапазона
     * @param priceBorder2 - верхняя граница диапазона
     * @return SearchPage page.getSearchPage - возвращает страницу поиска
     */
    public SearchPage setPriceBorders(Integer priceBorder1, Integer priceBorder2) {

        List<WebElement> priceBordersList = filters.findElements(By.xpath(".//div[@class='ui-c5']//input"));
        int count = 0;
        for (WebElement element : priceBordersList) {
            if (count > 2) {
                return this;
            } else if (count == 0) {
                clickAndFillElement(element, priceBorder1.toString(), true);
            } else if (count == 1) {
                clickAndFillElement(element, priceBorder2.toString(), true);
            }
            count++;
        }
        Assertions.fail("Элемент, содержащий диапазон цен, не найден");
        return this;

    }

    /**
     * Метод для выбора переключателей в списке фильтров
     *
     * @param checkboxName - название переключателя
     * @return SearchPage page.getSearchPage - возвращает страницу поиска
     */
    public SearchPage turnCheckBox(String checkboxName) {

        clickOnElement(convertIntoWebElement(filters, checkboxes, checkboxName));
        return page.getSearchPage();

    }

    /**
     * Метод для выбора дополнительных опций у товара, например "NFC", "3G", "Bluetooth" и т.п.
     *
     * @param checkboxName - название доп.опции
     * @return SearchPage page.getSearchPage - возвращает стартовую страницу
     */
    public SearchPage checkThisCheckBox(String checkboxName) {

        clickOnElement(convertIntoWebElement(filters, squareCheckboxes, checkboxName));
        return page.getSearchPage();

    }

    /**
     * Метод для проверки количества элементов в корзине
     *
     * @return int cartAmount - количество элементов в корзине типа integer
     */
    private int checkCartAmount() {

        int cartAmount;
        try {
            cartAmount = Integer.parseInt(cartCount.getText());
        } catch (NumberFormatException e) {
            cartAmount = 0;
        }
        return cartAmount;

    }

    /**
     * Метод для выбора названия брендов
     *
     * @param menu       - раздел фильтра, содержащий в себе название бренда
     * @param brandsName - названия брендов, которые нужно выбрать
     * @return SearchPage page.getSearchPage - возвращает страницу поиска
     */
    public SearchPage pickBrands(String menu, List<String> brandsName) {

        clickOnElement(convertIntoWebElement(filters, listOfSomething, menu));
        for (String s : brandsName) {
            clickAndFillElement(filters.findElement(By.xpath(".//input[@class='ui-av9 ui-av4']")), s, false);
            if (isVisible(By.xpath(String.format(brandsList, s)))) {
                clickOnElement(convertIntoWebElement(filters, brandsList, s));
            } else {
                System.out.println("Такого бренда нет в списке");
            }
            waitALittle(1000);
        }
        return this;

    }

    /**
     * Метод для добавления товаров в корзину
     *
     * @param amount  - количество товаров, которое хотим добавить
     * @param counter - шаг для добавления товара. 1 - добавлять подряд, 2 - каждый второй, 3 - каждый третий и т.д.
     * @return SearchPage page.getSearchPage() - страница результатов поиска
     */
    public SearchPage addToCart(Integer amount, int counter) {

        // xPath до кнопки "В корзину"
        String xButton = ".//button[contains(., 'В корзину')]";
        String naggerPopup = ".//div[@class='ui-a0q4']";
        String anotherDumbPopup = ".//div[@class='b5']";
        int i = counter - 1;
        while (i < productElements.size()) {
            if (checkCartAmount() < amount) {
                int count = checkCartAmount();
                WebElement element = productElements.get(i).findElement(By.xpath(xButton));
                scrollToElement(element);
                clickOnElement(element);
                if (isVisible(By.xpath(naggerPopup))) {
                    clickOnElement(getDriver().findElement(By.xpath(".//div[@qa-id='modal-close']")));
                } else if (isVisible(By.xpath(anotherDumbPopup))) {
                    element = productElements.get(i).findElement(By.xpath(xButton));
                    scrollToElement(element);
                    clickOnElement(element);
                } else {
                    boolean assertion = checkCartAmount() <= count;
                    int j = 0;
                    while (assertion) {
                        if (j > 5) {
                            assertion = false;
                        }
                        waitALittle(500);
                        j++;
                    }
                    addedProducts.add(productElements.get(i).findElement(By.xpath(productTitle)).getText());
                }
                i += counter;
            } else {
                return this;
            }
        }
        getNextPage(amount, counter);
        Assertions.fail("Количество товаров в корзине меньше требуемого, либо Веб-элементы отсутствуют");
        return this;

    }

    /**
     * Метод для добавления в корзину всех возможных элементов с шагом counter
     *
     * @param counter - шаг для добавления товара. 1 - добавлять подряд, 2 - каждый второй, 3 - каждый третий и т.д.
     * @return SearchPage page.getSearchPage - возвращает стартовую страницу
     */
    public SearchPage addToCart(int counter) {

        // xPath до кнопки "В корзину"
        String xButton = ".//button[contains(., 'В корзину')]";
        String naggerPopup = ".//div[@class='ui-a0q4']";
        String anotherDumbPopup = ".//div[@class='b5']";
        int i = counter - 1;
        while (i < productElements.size()) {
            WebElement element = productElements.get(i).findElement(By.xpath(xButton));
            scrollToElement(element);
            clickOnElement(element);
            if (isVisible(By.xpath(naggerPopup))) {
                clickOnElement(getDriver().findElement(By.xpath(".//div[@qa-id='modal-close']")));
            } else if (isVisible(By.xpath(anotherDumbPopup))) {
                element = productElements.get(i).findElement(By.xpath(xButton));
                scrollToElement(element);
                clickOnElement(element);
            } else {
                waitALittle(2000);
                addedProducts.add(productElements.get(i).findElement(By.xpath(productTitle)).getText());
            }
            i += counter;
        }
        getNextPage(counter);
        Assertions.fail("Количество товаров в корзине меньше требуемого, либо Веб-элементы отсутствуют");
        return this;

    }

    /**
     * Метод для перехода на следующую страницу
     *
     * @return SearchPage page.getSearchPage - возвращает страницу поиска
     * @see SearchPage#addToCart(Integer amount, int counter)
     */
    private SearchPage getNextPage(Integer amount, int counter) {

        String xNextPage = ".//a[contains(@class, 'b9g0')]";
        List<WebElement> nextPage = getDriver().findElements(By.xpath(xNextPage));
        for (WebElement element : nextPage) {
            if (element.getText().equals(pageNumber.toString())) {
                pageNumber++;
                clickOnElement(element);
                addToCart(amount, counter);
                return this;
            }
        }
        System.out.println("Это последняя страница");
        pageNumber = 2;
        return this;
    }

    /**
     * Метод для перехода на следующую страницу
     *
     * @return SearchPage page.getSearchPage - возвращает страницу поиска
     * @see SearchPage#addToCart(int counter)
     */
    private SearchPage getNextPage(int counter) {

        String xNextPage = ".//a[contains(@class, 'b9g0')]";
        List<WebElement> nextPage = getDriver().findElements(By.xpath(xNextPage));
        for (WebElement element : nextPage) {
            if (element.getText().equals(pageNumber.toString())) {
                pageNumber++;
                clickOnElement(element);
                addToCart(counter);
                return this;
            }
        }
        System.out.println("Это последняя страница");
        pageNumber = 2;
        return this;
    }

}
