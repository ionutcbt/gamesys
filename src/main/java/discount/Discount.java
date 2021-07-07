package discount;

import java.math.BigDecimal;
import java.util.function.Predicate;

public abstract class Discount<T> {

    private Predicate<T> discountCondition;

    Discount(Predicate<T> discountCondition) {
        this.discountCondition = discountCondition;
    }

    Predicate<T> getDiscountCondition() {
        return discountCondition;
    }

    public abstract BigDecimal calculateDiscount(T productOrPriceParameter, int quantity);
}