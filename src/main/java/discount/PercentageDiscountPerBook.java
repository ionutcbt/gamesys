package discount;

import java.math.BigDecimal;
import java.util.function.Predicate;

import book.Book;

public class PercentageDiscountPerBook<T extends Book> extends Discount<T> {

    private BigDecimal percentageValue;

    PercentageDiscountPerBook(Predicate<T> condition, BigDecimal percentageValue) {
        super(condition);
        this.percentageValue = percentageValue;
    }

    @Override
    public BigDecimal calculateDiscount(T book, int quantity) {
        return super.getDiscountCondition().test(book) ?
                this.percentageValue
                        .multiply(book.getPrice())
                        .multiply(BigDecimal.valueOf(quantity)) :
                new BigDecimal("0");
    }
}