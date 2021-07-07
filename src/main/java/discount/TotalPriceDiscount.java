package discount;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class TotalPriceDiscount<T extends BigDecimal> extends Discount<T> {

    private BigDecimal percentageValue;

    TotalPriceDiscount(Predicate<T> condition, BigDecimal percentageValue) {
        super(condition);
        this.percentageValue = percentageValue;
    }

    @Override
    public BigDecimal calculateDiscount(T totalPrice, int quantity) {
        return super.getDiscountCondition().test(totalPrice) ?
                this.percentageValue
                        .multiply(totalPrice)
                        .multiply(BigDecimal.valueOf(quantity)) :
                new BigDecimal("0");
    }
}
