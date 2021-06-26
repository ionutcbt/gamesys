package discount;

import java.math.BigDecimal;

public abstract class Discount {

    protected BigDecimal price;
    protected Integer quantity;

    protected Discount() {
    }

    public Discount(BigDecimal price, Integer quantity) {
        this.price = price;
        this.quantity = quantity;
    }

    public abstract BigDecimal calculate();
}