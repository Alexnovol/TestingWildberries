import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static steps.asserts.Gui.*;
import static utils.DataHelper.getChromeDriver;
import static utils.Locators.*;
import static utils.TimeHelper.setDelay;

public class GuiTest {

    private WebDriver driver = getChromeDriver();
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    private Actions action = new Actions(driver);
    private final String LINK = "https://www.wildberries.ru/";

    @Test
    @DisplayName("Проверка поисковой строки")
    public void checkSearchString() {

        driver.get(LINK);

        try {

            /* Ожидание загрузки элемента, после загрузки которого можно взаимодействовать с другими
            элементами
             */
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_MAIN_PAGE_CONTENT)));

            WebElement searchInput = driver.findElement(By.id(ID_SEARCH_INPUT));
            searchInput.click();
            searchInput.sendKeys("Iphone 13");
            searchInput.sendKeys(Keys.ENTER);

            String titleResults = driver.findElement(By.className(CLASS_TITLE_RESULTS)).getText();

            shouldBeEquals("По запросу Iphone 13 найдено", titleResults);

            String firstFilter = driver.findElement(By.xpath(XPATH_FIRST_FILTER))
                    .getText();

            shouldBeEquals("iphone 13", firstFilter);

            String secondFilter = driver.findElement(By.xpath(XPATH_SECOND_FILTER))
                    .getText();

            shouldBeEquals("По популярности", secondFilter);

            String firstProductBrand = driver.findElement(
                    By.xpath(XPATH_FIRST_PRODUCT + XPATH_BRAND_FIRST_PRODUCT))
                    .getText();

            shouldBeEquals("Apple", firstProductBrand);

            driver.findElement(By.cssSelector(CSS_BUTTON_CLEAR_SEARCH_FIELD))
                    .click();

            String valueSearchInput = searchInput.getAttribute("value");

            shouldBeEquals("", valueSearchInput);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка смены города")
    public void checkChangeCity() {

        driver.get(LINK);

        try {

            /* Ожидание загрузки элемента, после загрузки которого можно взаимодействовать с другими
            элементами
             */
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_MAIN_PAGE_CONTENT)));

            WebElement changeCity = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(XPATH_BUTTON_CHANGE_CITY)));
            changeCity.click();

            /* По непонятным причинам, иногда страница с вводом города открывается, но не загружается
            ни при каких ожиданиях. Приходится возвращаться на главную страницу и снова нажимать кнопку
            "Смена города", что реализовано в цикле ниже
             */
            WebElement searchInput = null;
            while (searchInput == null) {
                try {
                    searchInput = wait.until(
                            ExpectedConditions.elementToBeClickable(By.xpath(XPATH_ADDRESS_ENTRY)));
                    searchInput.click();
                    searchInput.sendKeys("Санкт-Петербург");
                } catch (NoSuchElementException e) {
                    driver.findElement(By.cssSelector(CSS_BUTTON_CLOSE))
                            .click();
                    changeCity.click();
                }
            }

            driver.findElement(By.xpath(XPATH_BUTTON_FIND))
                    .click();

            // Ожидание загрузки любого пункта выдачи в Санкт-Петербурге
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath(XPATH_ANY_DELIVERY_POINT)));

            WebElement firstAddress = driver.findElement(
                    By.xpath(XPATH_FIRST_DELIVERY_POINT));
            String expectedDeliveryAddress = firstAddress.findElement(
                    By.xpath(XPATH_ADDRESS_FIRST_DELIVERY_POINT)).getText();
            firstAddress.click();

            shouldBePresent(driver, By.className(CLASS_INFO_DELIVERY_POINT),
                    "Ожидалось открытие информации о центре выдачи, но ее нет");

            String actualDeliveryAddress = driver.findElement(
                    By.className(CLASS_ADDRESS_DELIVERY_POINT)).getText();

            shouldBeEquals(expectedDeliveryAddress, actualDeliveryAddress);

            driver.findElement(By.cssSelector(CSS_BUTTON_SELECT_DELIVERY_POINT))
                    .click();

            shouldBeAbsent(driver, By.className(CLASS_INFO_DELIVERY_POINT),
                    "Ожидалось открытие главной страницы, но ее нет");

            shouldBeEquals(expectedDeliveryAddress, changeCity.getText());

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Добавление товара в корзину")
    public void addProductBasket() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get(LINK);

        try {

            /* Ожидание загрузки элемента, после загрузки которого можно взаимодействовать с другими
            элементами
             */
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_MAIN_PAGE_CONTENT)));

            wait.until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector(CSS_BUTTON_FILTERS)))
                    .click();

            WebElement menu = driver.findElement(
                    By.cssSelector(CSS_CATEGORY_MENU));

            action.moveToElement(menu).perform();

            WebElement houseAppliances = driver.findElement(
                    By.cssSelector(CSS_HOUSE_APPLIANCES));

            js.executeScript("arguments[0].scrollIntoView();", houseAppliances);

            action.moveToElement(houseAppliances).perform();

            driver.findElement(
                            By.xpath(XPATH_APPLIANCES_FOR_HOUSE))
                    .click();

            driver.findElement(
                            By.xpath(XPATH_CATEGORY_HOOVERS_AND_STEAM_CLEANERS))
                    .click();

            driver.findElement(By.xpath(XPATH_CATEGORY_ROBOT_HOOVERS))
                    .click();

            String catalogTitle = driver.findElement(By.className(CLASS_CATALOG_TITLE)).getText();

            shouldBeEquals("Роботы-пылесосы", catalogTitle);

            List<String> actualFilters = new ArrayList<>();

            for (WebElement filter : driver.findElements(By.className(CLASS_FILTERS_ON_PAGE))) {
                String sFilter = filter.findElement(By.tagName("span")).getText();
                actualFilters.add(sFilter);
            }

            List<String> expectedFilters = Arrays
                    .asList("Главная", "Бытовая техника", "Техника для дома", "Пылесосы и пароочистители", "Роботы-пылесосы");

            shouldBeEquals(expectedFilters, actualFilters);

            WebElement firstProduct = driver.findElement(
                    By.xpath(XPATH_FIRST_PRODUCT));
            String productName = firstProduct.findElement(
                    By.xpath(XPATH_NAME_FIRST_PRODUCT)).getText()
                    .replace("/ ", "");
            String productPrice = firstProduct.findElement(
                    By.xpath(XPATH_PRICE_FIRST_PRODUCT)).getText();

            WebElement inBasket = driver.findElement(
                    By.xpath(XPATH_FIRST_PRODUCT + XPATH_IN_BASKET_BUTTON));
            js.executeScript("arguments[0].scrollIntoView();", inBasket);
            inBasket.click();

            By countProductsBasket = By.className(CLASS_COUNT_PRODUCTS_BASKET);

            shouldBePresent(driver, countProductsBasket,
                    "Над логотипом Корзины должен был появиться счётчик товаров, но этого не произошло");

            shouldBeEquals("1", driver.findElement(countProductsBasket).getText());

            driver.findElement(By.xpath(XPATH_BASKET_BUTTON))
                    .click();

            shouldBeEquals(productName, driver.findElement(By.className(CLASS_NAME_PRODUCT)).getText());

            setDelay(1);

            shouldBeEquals(productPrice, driver.findElement(By.className(CLASS_PRICE_PRODUCT)).getText());

            shouldBeEquals(productPrice, driver.findElement(
                    By.xpath(XPATH_PRICE_TOTAL)).getText());

            WebElement order = driver.findElement(By.cssSelector(CSS_ORDER_BUTTON));

            shouldBeEquals(true, order.isEnabled());



        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Работа с фильтрами")
    public void checkFilters() {

        driver.get(LINK);

        try {

            /* Ожидание загрузки элемента, после загрузки которого можно взаимодействовать с другими
            элементами
             */
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_MAIN_PAGE_CONTENT)));

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CSS_BUTTON_FILTERS)))
                    .click();

            WebElement electronics = wait.until(ExpectedConditions.elementToBeClickable(
                    driver.findElement(By.xpath(XPATH_ELECTRONICS_CATEGORY))));

            action.moveToElement(electronics).perform();

            /*
            Иногда почему то с первого раза не наводится на категорию "Электроника". Цикл ниже
            обрабатывает этот случай
             */
            WebElement laptopsAndComputers = null;
            int count = 0;
            while (laptopsAndComputers == null && count < 10) {
                count++;
                try {
                    laptopsAndComputers = driver.findElement(By.xpath(XPATH_LAPTOPS_AND_COMPUTERS_CATEGORY));
                    laptopsAndComputers.click();
                } catch (NoSuchElementException e) {
                    action.moveToElement(electronics).perform();
                }
            }

            driver.findElement(By.xpath(XPATH_LAPTOPS_CATEGORY))
                    .click();

            driver.findElement(By.cssSelector(CSS_BUTTON_ALL_FILTERS))
                    .click();

            WebElement priceFrom = driver.findElement(
                    By.xpath(XPATH_FILTER_PRICE_FROM));
            priceFrom.clear();
            priceFrom.sendKeys("100 000");
            WebElement priceTo = driver.findElement(By.xpath(XPATH_FILTER_PRICE_TO));
            priceTo.clear();
            priceTo.sendKeys("149 000");

            wait.until(ExpectedConditions.textToBePresentInElementValue(priceFrom, "100 000"));
            wait.until(ExpectedConditions.textToBePresentInElementValue(priceTo, "149 000"));

            WebElement brand = null;
            while (brand == null) {
                try {
                    brand = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(XPATH_FILTER_BRAND)));
                    brand.click();
                } catch (StaleElementReferenceException e) {
                    brand = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(XPATH_FILTER_BRAND)));
                    brand.click();
                }
            }

            WebElement diagonal = null;
            while (diagonal == null) {
                try {
                    diagonal = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(XPATH_FILTER_DIAGONAL)));
                    diagonal.click();
                } catch (StaleElementReferenceException e) {
                    diagonal = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath(XPATH_FILTER_DIAGONAL)));
                    diagonal.click();
                }
            }

            setDelay(2);

            String countProducts = driver.findElement(
                    By.className(CLASS_COUNT_FILTERED_GOODS_IN_FILTERS)).getText()
                    .replaceAll("\\D+", "");

            driver.findElement(By.cssSelector(CSS_BUTTON_SHOWING))
                    .click();

            shouldBeEquals(countProducts, driver.findElement(
                            By.xpath(XPATH_COUNT_FILTERED_GOODS_ON_PAGE)).getText()
                    .replaceAll(" ", ""));

            By locatorBrand = By.xpath(XPATH_SELECTED_FILTER_BRAND);

            shouldBePresent(driver, locatorBrand, "Ожидался фильтр Бренда на странице, но его нет");

            shouldBeEquals("Apple", driver.findElement(locatorBrand).getText());

            By locatorPrice = By.xpath(XPATH_SELECTED_FILTER_PRICE);

            shouldBePresent(driver, locatorPrice, "Ожидался фильтр Цены на странице, но его нет");

            shouldBeEquals("от 100 000 до 149 000", driver.findElement(locatorPrice).getText());

            By locatorDiagonal = By.xpath(XPATH_SELECTED_FILTER_DIAGONAL);

            shouldBePresent(driver, locatorDiagonal, "Ожидался фильтр Диагонали на странице, но его нет");

            shouldBeEquals("13.3\"", driver.findElement(locatorDiagonal).getText());

            By locatorButton = By.xpath(XPATH_BUTTON_RESET);

            shouldBePresent(driver, locatorButton,
                    "Ожидалась кнопка \"Сбросить все\" на странице, но ее нет");

            shouldBeEquals("Сбросить все", driver.findElement(locatorButton).getText());

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
