package discount;

import java.math.BigDecimal;

public class PercentageDiscount extends Discount {

    private BigDecimal percentage;

    public PercentageDiscount(BigDecimal price, Integer quantity, BigDecimal percentage) {
        super(price, quantity);
        this.percentage = percentage;
    }

    public BigDecimal calculate() {
        return price.multiply(new BigDecimal(String.valueOf(quantity))).multiply(this.percentage);
    }

}