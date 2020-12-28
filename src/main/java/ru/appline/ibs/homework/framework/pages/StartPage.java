package ru.appline.ibs.homework.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Стартовая страница
 */

public class StartPage extends BasePage {

    /**
     * Веб-элемент всплывающего окна
     */
    @FindBy(xpath = ".//h2[contains(text(), 'Вы находитесь в зоне очень быстрой доставки!')]/../..//button")
    WebElement naggerButton;

    /**
     * Проверка, является ли открытая страница стартовой
     *
     * @return StartPage page.getStartPage - возвращает стартовую страницу
     */
    public StartPage checkStartPage() {

        Assertions.assertEquals("OZON — интернет-магазин. Миллионы товаров по выгодным ценам",
                getPageTitle(), "Заголовок страницы не соответсвует ожидаемому");
        if (naggerButton.isDisplayed()) {
            clickOnElement(naggerButton);
        }
        return this;

    }

    /**
     * Метод для поиска товара
     *
     * @param productName - наименование товара
     * @return SearchPage page.getSearchPage - возвращает страницу поиска
     */
    public SearchPage searchProduct(String productName) {

        WebElement search = headMenu.findElement(By.xpath(".//input[@type]"));
        clickAndFillElement(search, productName, true);
        return page.getSearchPage();

    }
}
