package book;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BookListLoader {

    private Map<Book, Integer> purchasedBooks = new HashMap<>();

    public BookListLoader(String cartPath) {
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

    private void storeBook(JSONObject json) {
        Book book = getBookFromJson(json);
        if (purchasedBooks.containsKey(book)) {
            purchasedBooks.put(book, purchasedBooks.get(book) + 1);
        } else {
            purchasedBooks.put(book, 1);
        }
    }

    private Book getBookFromJson(JSONObject json) {
        JSONObject bookObject = (JSONObject) json.get("book");
        String title = (String) bookObject.get("title");
        BigDecimal price = new BigDecimal((String) bookObject.get("price"));
        Long year = (Long) bookObject.get("year");

        return new Book(title, price, Year.of(year.intValue()));
    }

    public Map<Book, Integer> getPurchasedBooks() {
        return purchasedBooks;
    }
}
