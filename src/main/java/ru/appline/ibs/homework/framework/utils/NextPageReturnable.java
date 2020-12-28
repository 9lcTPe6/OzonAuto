package ru.appline.ibs.homework.framework.utils;

import ru.appline.ibs.homework.framework.pages.BasePage;

/**
 * Функциональный интерфейс, отвечающий за возможность страницы возвращать ссылку на другую страницу
 */
@FunctionalInterface
public interface NextPageReturnable {

    BasePage getNextPage(String pageName);

}
