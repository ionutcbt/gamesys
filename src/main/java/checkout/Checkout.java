package checkout;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import book.Book;
import book.BookListLoader;
import discount.Discount;
import discount.DiscountLoader;

public class Checkout {

    private static final int PRICE_SCALE = 2;
    private static final int FINAL_PRICE_QUANTITY = 1;

    private BigDecimal totalPrice = new BigDecimal("0");
    private BigDecimal finalPrice = new BigDecimal("0");
    private BigDecimal totalDiscount = new BigDecimal("0");

    private Map<Book, Integer> purchasedBooks;
    private List<Discount<Book>> productDiscountList;
    private Discount<BigDecimal> totalCartDiscount;


    public Checkout(String cartPath) {
        BookListLoader bookListLoader = new BookListLoader(cartPath);
        purchasedBooks = bookListLoader.getPurchasedBooks();

        DiscountLoader discountLoader = new DiscountLoader();
        this.productDiscountList = discountLoader.getProductDiscountList();
        this.totalCartDiscount = discountLoader.getTotalCartDiscount();
    }

    public void processPrice() {
        calculateTotalPrice();
        applyDiscounts();
        setPriceScale();
    }

    private void calculateTotalPrice() {
        totalPrice = purchasedBooks.entrySet().stream()
                .map(e -> e.getKey().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private void applyDiscounts() {
        applyIndividualDiscounts();
        applyTotalPriceDiscount();
    }

    private void applyIndividualDiscounts() {
        for (Discount<Book> discount : productDiscountList) {
            BigDecimal discountedValue = purchasedBooks.entrySet().stream()
                    .map(e -> discount.calculateDiscount(e.getKey(), e.getValue()))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalDiscount = totalDiscount.add(discountedValue);
        }
        finalPrice = totalPrice.subtract(totalDiscount);
    }

    private void applyTotalPriceDiscount() {
        BigDecimal discountedValue = totalCartDiscount.calculateDiscount(finalPrice, FINAL_PRICE_QUANTITY);
        totalDiscount = totalDiscount.add(discountedValue);
        finalPrice = finalPrice.subtract(discountedValue);
    }

    private void setPriceScale() {
        finalPrice = finalPrice.setScale(PRICE_SCALE, RoundingMode.DOWN);
        totalDiscount = totalDiscount.setScale(PRICE_SCALE, RoundingMode.DOWN);
        totalPrice = totalPrice.setScale(PRICE_SCALE, RoundingMode.DOWN);
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public BigDecimal getTotalDiscount() {
        return this.totalDiscount;
    }

    public BigDecimal getFinalPrice() {
        return this.finalPrice;
    }
}
