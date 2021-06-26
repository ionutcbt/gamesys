package checkout;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import book.Book;
import discount.Discount;
import discount.PercentageDiscount;

public class Checkout {

    private final int PRICE_SCALE = 2;
    private final int FINAL_PRICE_QUANTITY = 1;
    private final int REFERENCE_YEAR = 2000;
    private final BigDecimal discountByYearPercentage = new BigDecimal("0.1");
    private final BigDecimal discountByTotalPricePercentage = new BigDecimal("0.05");
    private final BigDecimal totalPriceReference = new BigDecimal("30");

    private BigDecimal totalPrice = new BigDecimal("0");
    private BigDecimal finalPrice = new BigDecimal("0");
    private BigDecimal totalDiscount = new BigDecimal("0");
    private Map<Book, Integer> purchasedBooks = new HashMap<>();

    public Checkout(String cartPath) {
        loadBookList(cartPath);
    }

    private void loadBookList(String cartPath) {
        JSONParser jsonParser = new JSONParser();
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(cartPath)) {
            Reader targetReader = new InputStreamReader(inputStream);
            Object obj = jsonParser.parse(targetReader);
            JSONArray bookList = (JSONArray) obj;
            bookList.forEach( b -> storeBook( (JSONObject) b ) );
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void storeBook(JSONObject bookJson) {
        JSONObject bookObject = (JSONObject) bookJson.get("book");
        String title = (String) bookObject.get("title");
        BigDecimal price = new BigDecimal((String) bookObject.get("price"));
        Long year = (Long) bookObject.get("year");

        Book book = new Book(title, price, Year.of(year.intValue()));

        if (purchasedBooks.containsKey(book)) {
            purchasedBooks.put(book, purchasedBooks.get(book) + 1);
        } else {
            purchasedBooks.put(book, 1);
        }

    }

    public void applyDiscount() {
        applyYearTypeDiscount();
        applyTotalPriceTypeDiscount();
        roundPriceDecimals();
    }

    private void applyYearTypeDiscount() {
        purchasedBooks.forEach((book, quantity) -> {
            totalPrice = totalPrice.add(book.getPrice());
            if (book.getYear().compareTo(Year.of(REFERENCE_YEAR)) > 0) {
                BigDecimal discountedValue = getDiscountedValue(book.getPrice(), quantity, discountByYearPercentage);
                totalDiscount = totalDiscount.add(discountedValue);
            }
        });
    }

    private void applyTotalPriceTypeDiscount() {
        finalPrice = totalPrice.subtract(totalDiscount);
        if (finalPrice.compareTo(totalPriceReference) > 0) {
            BigDecimal discountedValue = getDiscountedValue(finalPrice, FINAL_PRICE_QUANTITY, discountByTotalPricePercentage);
            totalDiscount = totalDiscount.add(discountedValue);
            finalPrice = totalPrice.subtract(totalDiscount);
        }
    }

    private void roundPriceDecimals() {
        finalPrice = finalPrice.setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        totalDiscount = totalDiscount.setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
        totalPrice = totalPrice.setScale(PRICE_SCALE, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getDiscountedValue(BigDecimal price, Integer quantity, BigDecimal percentage) {
        Discount discount = new PercentageDiscount(price, quantity, percentage);
        return discount.calculate();
    }

    public BigDecimal getTotalDiscount() {
        return this.totalDiscount;
    }

    public BigDecimal getFinalPrice() {
        return this.finalPrice;
    }

    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

}
