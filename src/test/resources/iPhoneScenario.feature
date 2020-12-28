#language: ru

@all
  @iPhone
Функционал: Покупка iPhone

  @success
  Сценарий: : Покупка товара iPhone
    * Перейти на стартовую страницу
    * Проверить, что это стартовая страница
    * Выполнить поиск по 'iphone'
    * Проверить, что открылась страница поиска
    * Ограничить цену от '0' до '100000'
    * Отметить чекбокс 'Высокий рейтинг'
    * Выделить чекбокс 'NFC'
    * Добавить в корзину '8' товаров, добавляя каждый '2' товар
    * Перейти в корзину
    * Проверить, что открылась страница корзины
    * Проверить в корзине наличие товаров
    * Проверить отображаемый текст 'В вашей корзине...'
    * Записать название и цену самого дорогого добавленного товара
    * Удалить все товары
    * Проверить отсутсвие товаров в корзине
