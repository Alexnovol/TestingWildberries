package steps.asserts;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Gui {

    public static <T> void shouldBeEquals(T expected, T actual) {

        assertEquals(expected, actual);
    }

    public static void shouldBePresent(WebDriver driver, By locator, String errorMessage) {
        boolean present;

        try {
            driver.findElement(locator);
            present = true;
        } catch (NoSuchElementException e) {
            present = false;
        }

        assertTrue(present, errorMessage);
    }

    public static void shouldBeAbsent(WebDriver driver, By locator, String errorMessage) {
        boolean absent;

        try {
            driver.findElement(locator);
            absent = false;
        } catch (NoSuchElementException e) {
            absent = true;
        }

        assertTrue(absent, errorMessage);
    }
}
