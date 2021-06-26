package discount;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PercentageDiscountTest {

    @Test
    void shouldReturn10PercentDiscount() {
        PercentageDiscount percentageDiscount = new PercentageDiscount(new BigDecimal("100"), 1, new BigDecimal("0.1"));

        BigDecimal expected = new BigDecimal("10");
        BigDecimal actual = percentageDiscount.calculate();

        Assertions.assertTrue(expected.compareTo(actual) == 0, "Actual discount is different than expected one, when applying a 10% discount.");
    }

    @Test
    void shouldReturn5PercentDiscount() {
        PercentageDiscount percentageDiscount = new PercentageDiscount(new BigDecimal("100"), 1, new BigDecimal("0.05"));

        BigDecimal expected = new BigDecimal("5");
        BigDecimal actual = percentageDiscount.calculate();

        Assertions.assertTrue(expected.compareTo(actual) == 0, "Actual discount is different than expected one, when applying a 5% discount.");
    }
}