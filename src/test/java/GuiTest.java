import org.junit.jupiter.api.BeforeEach;
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
import static utils.TimeHelper.setDelay;

public class GuiTest {

    @BeforeEach
    public void setProperty() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
    }

    @Test
    @DisplayName("Проверка поисковой строки")
    public void checkSearchString() {

        WebDriver driver = getChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.get("https://www.wildberries.ru/");

        try {

            /* Ожидание загрузки элемента, после загрузки которого можно взаимодействовать с другими
            элементами
             */
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@class='main-page__content']//article[1]")));

            WebElement searchInput = driver.findElement(By.id("searchInput"));
            searchInput.click();
            searchInput.sendKeys("Iphone 13");
            searchInput.sendKeys(Keys.ENTER);

            String titleResults = driver.findElement(By.className("searching-results__title")).getText();

            shouldBeEquals("По запросу Iphone 13 найдено", titleResults);

            String firstFilter = driver.findElement(
                            By.xpath("//*[@class='filters-block__dropdown j-filtres-container']/div[@class='dropdown-filter'][1]/button"))
                    .getText();

            shouldBeEquals("iphone 13", firstFilter);

            String secondFilter = driver.findElement(
                            By.xpath("//*[@class='filters-block__dropdown j-filtres-container']/div[@class='dropdown-filter'][2]/button"))
                    .getText();

            shouldBeEquals("По популярности", secondFilter);

            String firstProductBrand = driver.findElement(
                            By.xpath("//*[@class='product-card-list']/article[1]//span[@class='product-card__brand']"))
                    .getText();

            shouldBeEquals("Apple", firstProductBrand);

            driver.findElement(
                            By.cssSelector(".search-catalog__btn.search-catalog__btn--clear.search-catalog__btn--active"))
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

        WebDriver driver = getChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.get("https://www.wildberries.ru/");

        try {

            /* Ожидание загрузки элемента, после загрузки которого можно взаимодействовать с другими
            элементами
             */
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@class='main-page__content']//article[1]")));

            WebElement changeCity = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[@data-wba-header-name='DLV_Adress']")));
            changeCity.click();

            /* По непонятным причинам, иногда страница с вводом города открывается, но не загружается
            ни при каких ожиданиях. Приходится возвращаться на главную страницу и снова нажимать кнопку
            "Смена города", что реализовано в цикле ниже
             */
            WebElement searchInput = null;
            while (searchInput == null) {
                try {
                    searchInput = wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath("//input[@placeholder='Введите адрес' and @autocomplete]")));
                    searchInput.click();
                    searchInput.sendKeys("Санкт-Петербург");
                } catch (NoSuchElementException e) {
                    driver.findElement(By.cssSelector(".popup__close-btn.j-btn-close"))
                            .click();
                    changeCity.click();
                }
            }

            driver.findElement(
                            By.xpath("//*[@class='ymaps-2-1-79-searchbox-button ymaps-2-1-79-_pin_right ymaps-2-1-79-user-selection-none']/ymaps"))
                    .click();

            setDelay(7);

            WebElement firstAddress = driver.findElement(
                    By.xpath("//*[@id='pooList']/div[1]"));
            String expectedDeliveryAddress = firstAddress.findElement(
                    By.xpath("//span[@class='address-item__name-text']/span")).getText();
            firstAddress.click();

            shouldBePresent(driver, By.className("details-self__title"),
                    "Ожидалось открытие информации о центре выдачи, но ее нет");

            String actualDeliveryAddress = driver.findElement(By.className("details-self__name-text")).getText();

            shouldBeEquals(expectedDeliveryAddress, actualDeliveryAddress);

            driver.findElement(By.cssSelector(".details-self__btn.btn-main"))
                    .click();

            shouldBeAbsent(driver, By.className("details-self__title"),
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

        WebDriver driver = getChromeDriver();
        Actions action = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.get("https://www.wildberries.ru/");

        try {

            /* Ожидание загрузки элемента, после загрузки которого можно взаимодействовать с другими
            элементами
             */
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@class='main-page__content']//article[1]")));

            wait.until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector(".nav-element__burger.j-menu-burger-btn.j-wba-header-item")))
                    .click();

            WebElement menu = driver.findElement(
                    By.cssSelector(".menu-burger__main.j-menu-burger-main.j-menu-active"));

            action.moveToElement(menu).perform();

            WebElement houseAppliances = driver.findElement(
                    By.cssSelector(".menu-burger__main-list-link.menu-burger__main-list-link--16107"));

            js.executeScript("arguments[0].scrollIntoView();", houseAppliances);

            action.moveToElement(houseAppliances).perform();

            driver.findElement(
                            By.xpath("//div[@data-menu-id='16107']/div/div/ul/li[4]/span"))
                    .click();

            driver.findElement(
                            By.xpath("//div[@data-menu-id='16107']/div/div[2]/ul/li[5]/span"))
                    .click();

            driver.findElement(By.xpath("//div[@data-menu-id='16107']//*[text()='Роботы-пылесосы']"))
                    .click();

            String catalogTitle = driver.findElement(By.className("catalog-title")).getText();

            shouldBeEquals("Роботы-пылесосы", catalogTitle);

            List<String> actualFilters = new ArrayList<>();

            for (WebElement filter : driver.findElements(By.className("breadcrumbs__item"))) {
                String sFilter = filter.findElement(By.tagName("span")).getText();
                actualFilters.add(sFilter);
            }

            List<String> expectedFilters = Arrays
                    .asList("Главная", "Бытовая техника", "Техника для дома", "Пылесосы и пароочистители", "Роботы-пылесосы");

            shouldBeEquals(expectedFilters, actualFilters);

            WebElement firstProduct = driver.findElement(
                    By.xpath("//*[@class='product-card-list']/article[1]"));
            String productName = firstProduct.findElement(
                    By.xpath("//*[@class='product-card__name']")).getText()
                    .replace("/ ", "");
            String productPrice = firstProduct.findElement(
                    By.xpath("//*[@class='price__wrap']/ins")).getText();

            WebElement inBasket = driver.findElement(
                    By.xpath("//*[@class='product-card-list']/article[1]//*[@href='/lk/basket']"));
            js.executeScript("arguments[0].scrollIntoView();", inBasket);
            inBasket.click();

            By countProductsBasket = By.className("navbar-pc__notify");

            shouldBePresent(driver, countProductsBasket,
                    "Над логотипом Корзины должен был появиться счётчик товаров, но этого не произошло");

            shouldBeEquals("1", driver.findElement(countProductsBasket).getText());

            driver.findElement(By.xpath("//*[@class='navbar-pc__item j-item-basket']/a"))
                    .click();

            shouldBeEquals(productName, driver.findElement(By.className("good-info__good-name")).getText());

            setDelay(1);

            shouldBeEquals(productPrice, driver.findElement(By.className("list-item__price-new")).getText());

            shouldBeEquals(productPrice, driver.findElement(
                    By.xpath("//*[@class='b-top__total line']/span[2]")).getText());

            WebElement order = driver.findElement(By.cssSelector(".b-btn-do-order.btn-main.j-btn-confirm-order"));

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

        WebDriver driver = getChromeDriver();
        Actions action = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        driver.get("https://www.wildberries.ru/");

        try {

            /* Ожидание загрузки элемента, после загрузки которого можно взаимодействовать с другими
            элементами
             */
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@class='main-page__content']//article[1]")));

            wait.until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector(".nav-element__burger.j-menu-burger-btn.j-wba-header-item")))
                    .click();

            WebElement electronics = wait.until(ExpectedConditions.elementToBeClickable(
                    driver.findElement(By.xpath("//li[@data-menu-id='4830']/span"))));

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
                    laptopsAndComputers = driver.findElement(By.xpath("//div[@data-menu-id='4830']//ul/li[7]/span"));
                    laptopsAndComputers.click();
                } catch (NoSuchElementException e) {
                    action.moveToElement(electronics).perform();
                }
            }

            driver.findElement(By.xpath("//div[@data-menu-id='4830']/div/div[2]/ul/li[1]/a"))
                    .click();

            driver.findElement(By.cssSelector(".dropdown-filter.j-show-all-filtres"))
                    .click();

            WebElement priceFrom = driver.findElement(
                    By.xpath("//*[@class='filter__price']/div[1]//input"));
            priceFrom.clear();
            priceFrom.sendKeys("100 000");
            WebElement priceTo = driver.findElement(By.xpath("//*[@class='filter__price']/div[2]//input"));
            priceTo.clear();
            priceTo.sendKeys("149 000");

            wait.until(ExpectedConditions.textToBePresentInElementValue(priceFrom, "100 000"));
            wait.until(ExpectedConditions.textToBePresentInElementValue(priceTo, "149 000"));

            WebElement brand = null;
            while (brand == null) {
                try {
                    brand = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@class='filters-desktop__item j-filter-container filters-desktop__item--type-1 filters-desktop__item--fbrand open show']//*[text()='Apple']/preceding-sibling::span")));
                    brand.click();
                } catch (StaleElementReferenceException e) {
                    brand = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@class='filters-desktop__item j-filter-container filters-desktop__item--type-1 filters-desktop__item--fbrand open show']//*[text()='Apple']/preceding-sibling::span")));
                    brand.click();
                }
            }

            WebElement diagonal = null;
            while (diagonal == null) {
                try {
                    diagonal = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@class='filters-desktop__item j-filter-container filters-desktop__item--type-1 filters-desktop__item--f4474 open show']//*[text()='13.3\"']/preceding-sibling::span")));
                    diagonal.click();
                } catch (StaleElementReferenceException e) {
                    diagonal = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//*[@class='filters-desktop__item j-filter-container filters-desktop__item--type-1 filters-desktop__item--f4474 open show']//*[text()='13.3\"']/preceding-sibling::span")));
                    diagonal.click();
                }
            }

            setDelay(2);

            String countProducts = driver.findElement(By.className("filters-desktop__count-goods")).getText()
                    .replaceAll("\\D+", "");

            driver.findElement(By.cssSelector(".filters-desktop__btn-main.btn-main"))
                    .click();

            shouldBeEquals(countProducts, driver.findElement(
                            By.xpath("//*[@class='goods-count']/span[1]")).getText()
                    .replaceAll(" ", ""));

            By locatorBrand = By.xpath("//*[@class='your-choice__list']/li[1]/span");

            shouldBePresent(driver, locatorBrand, "Ожидался фильтр Бренда на странице, но его нет");

            shouldBeEquals("Apple", driver.findElement(locatorBrand).getText());

            By locatorPrice = By.xpath("//*[@class='your-choice__list']/li[2]/span");

            shouldBePresent(driver, locatorPrice, "Ожидался фильтр Цены на странице, но его нет");

            shouldBeEquals("от 100 000 до 149 000", driver.findElement(locatorPrice).getText());

            By locatorDiagonal = By.xpath("//*[@class='your-choice__list']/li[3]/span");

            shouldBePresent(driver, locatorDiagonal, "Ожидался фильтр Диагонали на странице, но его нет");

            shouldBeEquals("13.3\"", driver.findElement(locatorDiagonal).getText());

            By locatorButton = By.xpath("//*[@class='your-choice__list']/li[4]/button");

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
