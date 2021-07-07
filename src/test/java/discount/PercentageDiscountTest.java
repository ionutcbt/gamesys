package discount;

import java.math.BigDecimal;
import java.time.Year;
import java.util.function.Predicate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import book.Book;

class PercentageDiscountTest {

    @Test
    void shouldReturn10PercentDiscount() {
        Book book = new Book("Game of Thrones", new BigDecimal("100"), Year.of(2001));
        BigDecimal percentageToBeApplied = new BigDecimal("0.1");
        Predicate<Book> afterYear2000Condition = b -> Year.of(2000).compareTo(b.getYear()) < 0;
        Discount<Book> afterYear2000Discount = new PercentageDiscountPerBook<>(afterYear2000Condition, percentageToBeApplied);

        BigDecimal expected = new BigDecimal("20.0");
        BigDecimal actual = afterYear2000Discount.calculateDiscount(book, 2);

        Assertions.assertEquals(expected, actual, "Actual discount is different than expected one.");
    }

    @Test
    void shouldReturn5PercentDiscount() {
        BigDecimal totalPriceReference = new BigDecimal("30");
        BigDecimal actualPrice = new BigDecimal("100");
        BigDecimal percentageToBeApplied = new BigDecimal("0.05");
        Predicate<BigDecimal> totalPriceGreaterThan30Condition = price -> totalPriceReference.compareTo(price) < 0;
        Discount<BigDecimal> totalCartDiscount = new TotalPriceDiscount<>(totalPriceGreaterThan30Condition, percentageToBeApplied);

        BigDecimal expected = new BigDecimal("5.00");
        BigDecimal actual = totalCartDiscount.calculateDiscount(actualPrice, 1);

        Assertions.assertEquals(expected, actual, "Actual discount is different than expected one.");
    }
}