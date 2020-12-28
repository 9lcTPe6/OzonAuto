package ru.appline.ibs.homework.framework.managers;

import ru.appline.ibs.homework.framework.pages.BasePage;
import ru.appline.ibs.homework.framework.pages.CartPage;
import ru.appline.ibs.homework.framework.pages.SearchPage;
import ru.appline.ibs.homework.framework.pages.StartPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер страниц
 */
public class PageManager {

    private static PageManager pageManager;

    private static List<BasePage> pageList = new ArrayList<>();

    StartPage startPage;
    SearchPage searchPage;
    CartPage cartPage;

    /**
     * Конструктор на базе паттерна Singleton
     */
    private PageManager() {

    }

    /**
     * Геттер для PageManager
     *
     * @return PageManager pageManager
     */
    public static PageManager getPageManager() {

        if (pageManager == null) {
            pageManager = new PageManager();
        }
        return pageManager;

    }

    /**
     * Геттер для StartPage
     *
     * @return StartPage startPage - стартовая страница
     */
    public StartPage getStartPage() {

        if (startPage != null && !pageList.contains(startPage)) {
            startPage = null;
            startPage = new StartPage();
            pageList.add(startPage);
        } else if (startPage == null) {
            startPage = new StartPage();
            pageList.add(startPage);
        }
        return startPage;
    }

    /**
     * Геттер для SearchPage
     *
     * @return SearchPage searchPage - страница поиска
     */
    public SearchPage getSearchPage() {

        if (searchPage != null && !pageList.contains(searchPage)) {
            searchPage = null;
            searchPage = new SearchPage();
            pageList.add(searchPage);
        } else if (searchPage == null) {
            searchPage = new SearchPage();
            pageList.add(searchPage);
        }
        return searchPage;

    }

    /**
     * Геттер для корзины
     *
     * @return CartPage cartPage - страница корзины
     */
    public CartPage getCartPage() {

        if (cartPage != null && !pageList.contains(cartPage)) {
            cartPage = null;
            cartPage = new CartPage();
            pageList.add(cartPage);
        } else if (cartPage == null) {
            cartPage = new CartPage();
            pageList.add(cartPage);
        }
        return cartPage;
    }

    /**
     * Метод для сброса созданных страниц и очистка листа
     */
    static void cleanPages() {

        pageList.clear();

    }

}
