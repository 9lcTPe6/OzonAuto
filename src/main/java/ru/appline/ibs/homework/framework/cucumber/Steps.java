package ru.appline.ibs.homework.framework.cucumber;

import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Тогда;
import ru.appline.ibs.homework.framework.managers.PageManager;

import java.util.List;

public class Steps {

    private PageManager page = PageManager.getPageManager();

    @Когда("^Перейти на стартовую страницу")
    public void открытьСтартовуюСтраницу() {
        page.getStartPage();
    }

    @Когда("^Проверить, что это стартовая страница")
    public void проверкаСтартовойСтраницы() {
        page.getStartPage().checkStartPage();
    }

    @Тогда("^Выполнить поиск по '(.*)'$")
    public void найтиТовар(String productName) {
        page.getStartPage().searchProduct(productName);
    }

    @Когда("^Проверить, что открылась страница поиска")
    public void проверитьСтраницуПоиска() {
        page.getSearchPage().checkSearchPage();
    }

    @Тогда("^Ограничить цену от '(\\d+)' до '(\\d+)'$")
    public void установитьОграниченияЦены(Integer priceBorder1, Integer priceBorder2) {
        page.getSearchPage().setPriceBorders(priceBorder1, priceBorder2);
    }

    @Тогда("^Выбрать бренды$")
    public void выбратьБренды(List<String> brands) {
        page.getSearchPage().pickBrands("Бренды", brands);
    }

    @Тогда("^Отметить чекбокс '(.*)'$")
    public void переключитьТумлер(String checkBox) {
        page.getSearchPage().turnCheckBox(checkBox);
    }

    @Тогда("^Выделить чекбокс '(.*)'$")
    public void отметитьЧекбокс(String checkBox) {
        page.getSearchPage().checkThisCheckBox(checkBox);
    }

    @Тогда("^Добавить в корзину '(\\d+)' товаров, добавляя каждый '(\\d+)' товар$")
    public void добавитьУказанноеКоличествоТоваров(Integer amount, int count) {
        page.getSearchPage().addToCart(amount, count);
    }

    @Тогда("^Добавить в корзину все товары, добавляя каждый '(\\d+)' товар$")
    public void добавитьВсеТовары(int count) {
        page.getSearchPage().addToCart(count);
    }

    @Когда("^Перейти в корзину$")
    public void вКорзину() {
        page.getCartPage();
    }

    @Когда("^Проверить, что открылась страница корзины$")
    public void проверитьСтраницуКорзины() {
        page.getCartPage().checkCartPage();
    }

    @Тогда("^Проверить в корзине наличие товаров$")
    @Тогда("^Проверить отсутсвие товаров в корзине$")
    public void проверитьКорзину() {
        page.getCartPage().checkProductsInCart();
    }

    @Тогда("^Проверить отображаемый текст 'В вашей корзине...'$")
    public void проверитьИнформациюОКорзине() {
        page.getCartPage().checkCartProductsSum();
    }

    @Тогда("^Записать название и цену самого дорогого добавленного товара$")
    public void записатьДорожайший() {
        page.getCartPage().getHighestPrice();
    }

    @Когда("^Удалить все товары$")
    public void удалитьВсе() {
        page.getCartPage().removeAll();
    }

}
