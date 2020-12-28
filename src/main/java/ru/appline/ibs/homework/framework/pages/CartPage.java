package ru.appline.ibs.homework.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static ru.appline.ibs.homework.framework.cucumber.Hook.getReport;
import static ru.appline.ibs.homework.framework.pages.SearchPage.clearList;
import static ru.appline.ibs.homework.framework.pages.SearchPage.getAddedProducts;

/**
 * Страница корзины
 */
public class CartPage extends BasePage {

    /**
     * Список Веб-элементов с добавленными в корзину товарами
     */
    @FindBy(xpath = ".//div[@class='a7m4']")
    List<WebElement> productsInCart;

    /**
     * Веб-элемент, содержащий информацию об общей сумме покупок
     */
    @FindBy(xpath = ".//section[@data-widget='total']")
    WebElement totalInfo;

    /**
     * Веб-элемент с кнопкой "удалить всё"
     */
    @FindBy(xpath = ".//div[@delete_button_name]")
    WebElement deleteAllBtn;

    /**
     * count - счетчик для сравнения товаров в корзине с отображаемыми на странице
     * productList - словарь, содержащий в себе информацию о названии и цене добавленных в корзину товарах
     */
    private static Integer count = 0;
    private static Map<String, Integer> productList = new LinkedHashMap<>();

    /**
     * Метод для проверки, является ли страница - страницей корзины
     *
     * @return CartPage page.getCartPage - возвращает страницу корзины
     */
    public CartPage checkCartPage() {

        goToCart();
        Assertions.assertEquals("OZON.ru - Моя корзина", getPageTitle(),
                "Данная страница не является страницей корзины, либо заголовок не соответствует ожидаемому");
        return this;

    }

    /**
     * Метод для проверки товаров в корзине на их соответствие тем, что добавлялись на странице поиска
     *
     * @return CartPage page.getCartPage - возвращает страницу корзины
     */
    public CartPage checkProductsInCart() {

        List<String> addedProductList = getAddedProducts();
        try {
            for (WebElement element : productsInCart) {
                String equality = element.findElement(By.xpath(".//span[@style]")).getText();
                String sPrice = element.findElement(By.xpath(".//div[contains(@style, 'font-size: 15')]/span")).getText();
                Integer price = Integer.parseInt(sPrice.replaceAll("(\\D)", ""));
                productList.put(equality, price);
                for (String s : addedProductList) {
                    if (equality.equals(s)) {
                        count++;
                    }
                }
            }
            getReport("allAddedProducts");
        } catch (NoSuchElementException e) {
            Assertions.assertEquals(Integer.parseInt(cartCount.getText()), count,
                    "Количество товаров в после удаления не соответствует ожидаемому");
            return this;
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(Integer.parseInt(cartCount.getText()), count,
                "Количество товаров в корзине не соответствует ожидаемому");
        return this;

    }

    /**
     * Метод для проверки отображаемого значения в разделе "В вашей корзине"
     *
     * @return CartPage page.getCartPage - возвращает стартовую страницу
     */
    public CartPage checkCartProductsSum() {

        String xAmount = ".//span[@class='a5a5']";
        WebElement amount = totalInfo.findElement(By.xpath(xAmount));
        Assertions.assertEquals(count, Integer.parseInt(amount.getText().substring(0, 4).replaceAll("(\\D)", "")),
                "Количество товаров в корзине не соответсвует ожидаемому");
        return this;

    }

    /**
     * Метод для получения наибольшего значения в словаре
     *
     * @param map - словарь, передаваемый в метод со значениями Integer
     * @param <K> - любой тип ключей словаря
     * @return Map<K, Integer> max - массив с одним элементом, содержащим максимальное значение и ключ этого значения
     */
    private <K> Map<K, Integer> getHighest(Map<K, Integer> map) {

        int highest = 0;
        K k = null;
        for (Map.Entry<K, Integer> pairs : map.entrySet()) {
            K k1 = pairs.getKey();
            Integer i = pairs.getValue();
            if (highest < i) {
                highest = i;
                k = k1;
            }
        }
        Map<K, Integer> max = new HashMap<>();
        max.put(k, highest);

        return max;

    }

    @Step("Получение самого дорогого товара")
    public CartPage getHighestPrice() {

        System.out.println(getHighest(productList));
        try {
            getReport("report");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Метод для удаления всех товаров из корзины
     *
     * @return CartPage page.getCartPage - возвращает страницу корзины
     */
    public CartPage removeAll() {

        if (isVisible(By.xpath(".//div[@delete_button_name]//span"))) {
            WebElement element = deleteAllBtn.findElement(By.xpath(".//span"));
            clickOnElement(element);
            count = 0;
            clearList(getAddedProducts());
            productList.clear();
        }
        return this;

    }

}
