package discount;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import book.Book;

public class DiscountLoader {

    private static final int REFERENCE_YEAR_1800 = 1800;
    private static final BigDecimal BEFORE_YEAR_1800_DISCOUNT_PERCENTAGE = new BigDecimal("0.5");

    private static final int REFERENCE_YEAR_2000 = 2000;
    private static final BigDecimal AFTER_YEAR_2000_DISCOUNT_PERCENTAGE = new BigDecimal("0.1");

    private static final BigDecimal TOTAL_PRICE_REFERENCE = new BigDecimal("30");
    private static final BigDecimal TOTAL_PRICE_DISCOUNT_PERCENTAGE = new BigDecimal("0.05");

    private List<Discount<Book>> productDiscountList = new ArrayList<>();
    private Discount<BigDecimal> totalCartDiscount;

    public DiscountLoader() {
        loadExistingIndividualDiscounts();
        loadExistingTotalCartDiscount();
    }

    private void loadExistingIndividualDiscounts() {
        Predicate<Book> afterYear2000Condition = book -> Year.of(REFERENCE_YEAR_2000).compareTo(book.getYear()) < 0;
        Discount<Book> afterYear2000Discount = new PercentageDiscountPerBook<>(afterYear2000Condition, AFTER_YEAR_2000_DISCOUNT_PERCENTAGE);
        productDiscountList.add(afterYear2000Discount);

        Predicate<Book> beforeYear1900Condition = book -> Year.of(REFERENCE_YEAR_1800).compareTo(book.getYear()) > 0;
        Discount<Book> beforeYear2000Discount = new PercentageDiscountPerBook<>(beforeYear1900Condition, BEFORE_YEAR_1800_DISCOUNT_PERCENTAGE);
        productDiscountList.add(beforeYear2000Discount);
    }

    private void loadExistingTotalCartDiscount() {
        Predicate<BigDecimal> totalPriceGreaterThan30Condition = total -> TOTAL_PRICE_REFERENCE.compareTo(total) < 0;
        totalCartDiscount = new TotalPriceDiscount<>(totalPriceGreaterThan30Condition, TOTAL_PRICE_DISCOUNT_PERCENTAGE);
    }

    public boolean addIndividualDiscount(Discount<Book> discount) {
        return this.productDiscountList.add(discount);
    }

    public void addTotalCartDiscount(Discount<BigDecimal> discount) {
        this.totalCartDiscount = discount;
    }

    public List<Discount<Book>> getProductDiscountList() {
        return productDiscountList;
    }

    public void clearIndividualDiscountList() {
        this.productDiscountList.clear();
    }

    public Discount<BigDecimal> getTotalCartDiscount() {
        return totalCartDiscount;
    }

    public void clearTotalCartDiscount() {
        this.totalCartDiscount = null;
    }
}
