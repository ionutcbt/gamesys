package checkout;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckoutTest {

    @Test
    void shouldApplyOnly10PercentDiscount() {
        BigDecimal expectedFinalPrice = new BigDecimal("24.69");
        String bookListPath = "checkout/book-list-1.json";
        Checkout checkout = new Checkout(bookListPath);

        checkout.processPrice();
        BigDecimal actualFinalPrice = checkout.getFinalPrice();

        Assertions.assertEquals(expectedFinalPrice, actualFinalPrice, "Expected final price is different than the actual one.");
    }

    @Test
    void shouldApplyOnly5PercentDiscount() {
        BigDecimal expectedFinalPrice = new BigDecimal("35.27");
        String bookListPath = "checkout/book-list-2.json";
        Checkout checkout = new Checkout(bookListPath);

        checkout.processPrice();
        BigDecimal actualFinalPrice = checkout.getFinalPrice();

        Assertions.assertEquals(expectedFinalPrice, actualFinalPrice, "Expected final price is different than the actual one.");
    }

    @Test
    void shouldApplyBothPercentDiscounts() {
        BigDecimal expectedFinalPrice = new BigDecimal("36.01");
        String bookListPath = "checkout/book-list-3.json";
        Checkout checkout = new Checkout(bookListPath);

        checkout.processPrice();
        BigDecimal actualFinalPrice = checkout.getFinalPrice();

        Assertions.assertEquals(expectedFinalPrice, actualFinalPrice, "Expected final price is different than the actual one.");
    }

    @Test
    void shouldNotApplyDiscount() {
        BigDecimal expectedFinalPrice = new BigDecimal("11.05");
        String bookListPath = "checkout/book-list-4.json";
        Checkout checkout = new Checkout(bookListPath);

        checkout.processPrice();
        BigDecimal actualFinalPrice = checkout.getFinalPrice();

        Assertions.assertEquals(expectedFinalPrice, actualFinalPrice, "Expected final price is different than the actual one.");
    }

    @Test
    void shouldApplyBothDiscountsWhenLoadingAllBooks() {
        BigDecimal expectedFinalPrice = new BigDecimal("99.27");
        String bookListPath = "checkout/book-list-5.json";
        Checkout checkout = new Checkout(bookListPath);

        checkout.processPrice();
        BigDecimal actualFinalPrice = checkout.getFinalPrice();

        Assertions.assertEquals(expectedFinalPrice, actualFinalPrice, "Expected final price is different than the actual one.");
    }

    @Test
    void shouldApplyMultipleIndividualDiscountsWhenBuyingAllBooks() {
        BigDecimal expectedFinalPrice = new BigDecimal("4836.72");
        String bookListPath = "checkout/book-list-6.json";
        Checkout checkout = new Checkout(bookListPath);

        checkout.processPrice();
        BigDecimal actualFinalPrice = checkout.getFinalPrice();

        Assertions.assertEquals(expectedFinalPrice, actualFinalPrice, "Expected final price is different than the actual one.");
    }
}