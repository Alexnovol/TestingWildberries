package utils;

public class Locators {

    public final static String XPATH_MAIN_PAGE_CONTENT = "//*[@class='main-page__content']//article[1]";

    public final static String XPATH_FILTER_DIAGONAL = "//*[@class='filters-desktop__item j-filter-container filters-desktop__item--type-1 filters-desktop__item--f4474 open show']//*[text()='13.3\"']/preceding-sibling::span";

    public final static String XPATH_FILTER_BRAND = "//*[@class='filters-desktop__item j-filter-container filters-desktop__item--type-1 filters-desktop__item--fbrand open show']//*[text()='Apple']/preceding-sibling::span";

    public final static String CSS_BUTTON_FILTERS = ".nav-element__burger.j-menu-burger-btn.j-wba-header-item";

    public final static String XPATH_FIRST_PRODUCT = "//*[@class='product-card-list']/article[1]";

    public final static String CLASS_INFO_DELIVERY_POINT = "details-self__title";

    public final static String XPATH_BUTTON_RESET = "//*[@class='your-choice__list']/li[4]/button";

    public final static String XPATH_SELECTED_FILTER_DIAGONAL = "//*[@class='your-choice__list']/li[3]/span";

    public final static String XPATH_SELECTED_FILTER_PRICE = "//*[@class='your-choice__list']/li[2]/span";

    public final static String XPATH_SELECTED_FILTER_BRAND = "//*[@class='your-choice__list']/li[1]/span";

    public final static String XPATH_COUNT_FILTERED_GOODS_ON_PAGE = "//*[@class='goods-count']/span[1]";

    public final static String CSS_BUTTON_SHOWING = ".filters-desktop__btn-main.btn-main";

    public final static String CLASS_COUNT_FILTERED_GOODS_IN_FILTERS = "filters-desktop__count-goods";

    public final static String XPATH_FILTER_PRICE_TO = "//*[@class='filter__price']/div[2]//input";

    public final static String XPATH_FILTER_PRICE_FROM = "//*[@class='filter__price']/div[1]//input";

    public final static String CSS_BUTTON_ALL_FILTERS = ".dropdown-filter.j-show-all-filtres";

    public final static String XPATH_LAPTOPS_CATEGORY = "//div[@data-menu-id='4830']/div/div[2]/ul/li[1]/a";

    public final static String XPATH_LAPTOPS_AND_COMPUTERS_CATEGORY = "//div[@data-menu-id='4830']//ul/li[7]/span";

    public final static String XPATH_ELECTRONICS_CATEGORY = "//li[@data-menu-id='4830']/span";

    public final static String CSS_ORDER_BUTTON = ".b-btn-do-order.btn-main.j-btn-confirm-order";

    public final static String XPATH_PRICE_TOTAL = "//*[@class='b-top__total line']/span[2]";

    public final static String CLASS_PRICE_PRODUCT = "list-item__price-new";

    public final static String CLASS_NAME_PRODUCT = "good-info__good-name";

    public final static String XPATH_BASKET_BUTTON = "//*[@class='navbar-pc__item j-item-basket']/a";

    public final static String CLASS_COUNT_PRODUCTS_BASKET = "navbar-pc__notify";

    public final static String XPATH_IN_BASKET_BUTTON = "//*[@href='/lk/basket']";

    public final static String XPATH_PRICE_FIRST_PRODUCT = "//*[@class='price__wrap']/ins";

    public final static String XPATH_NAME_FIRST_PRODUCT = "//*[@class='product-card__name']";

    public final static String CLASS_FILTERS_ON_PAGE = "breadcrumbs__item";

    public final static String CLASS_CATALOG_TITLE = "catalog-title";

    public final static String XPATH_CATEGORY_ROBOT_HOOVERS = "//div[@data-menu-id='16107']//*[text()='Роботы-пылесосы']";

    public final static String XPATH_CATEGORY_HOOVERS_AND_STEAM_CLEANERS = "//div[@data-menu-id='16107']/div/div[2]/ul/li[5]/span";

    public final static String XPATH_APPLIANCES_FOR_HOUSE = "//div[@data-menu-id='16107']/div/div/ul/li[4]/span";

    public final static String CSS_HOUSE_APPLIANCES = ".menu-burger__main-list-link.menu-burger__main-list-link--16107";

    public final static String CSS_CATEGORY_MENU = ".menu-burger__main.j-menu-burger-main.j-menu-active";

    public final static String CSS_BUTTON_SELECT_DELIVERY_POINT = ".details-self__btn.btn-main";

    public final static String CLASS_ADDRESS_DELIVERY_POINT = "details-self__name-text";

    public final static String XPATH_ADDRESS_FIRST_DELIVERY_POINT = "//span[@class='address-item__name-text']/span";

    public final static String XPATH_FIRST_DELIVERY_POINT = "//*[@id='pooList']/div[1]";

    public final static String XPATH_ANY_DELIVERY_POINT = "//*[@id='pooList']//*[contains(text(), 'Санкт-Петербург')]";

    public final static String XPATH_BUTTON_FIND = "//*[@class='ymaps-2-1-79-searchbox-button ymaps-2-1-79-_pin_right ymaps-2-1-79-user-selection-none']/ymaps";

    public final static String CSS_BUTTON_CLOSE = ".popup__close-btn.j-btn-close";

    public final static String XPATH_ADDRESS_ENTRY = "//input[@placeholder='Введите адрес' and @autocomplete]";

    public final static String XPATH_BUTTON_CHANGE_CITY = "//span[@data-wba-header-name='DLV_Adress']";

    public final static String CSS_BUTTON_CLEAR_SEARCH_FIELD = ".search-catalog__btn.search-catalog__btn--clear.search-catalog__btn--active";

    public final static String XPATH_BRAND_FIRST_PRODUCT = "//span[@class='product-card__brand']";

    public final static String XPATH_SECOND_FILTER = "//*[@class='filters-block__dropdown j-filtres-container']/div[@class='dropdown-filter'][2]/button";

    public final static String XPATH_FIRST_FILTER = "//*[@class='filters-block__dropdown j-filtres-container']/div[@class='dropdown-filter'][1]/button";

    public final static String CLASS_TITLE_RESULTS = "searching-results__title";

    public final static String ID_SEARCH_INPUT = "searchInput";


}
