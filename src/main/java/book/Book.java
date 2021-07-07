package book;

import java.math.BigDecimal;
import java.time.Year;

public class Book {

    private String title;
    private BigDecimal price;
    private Year year;

    public Book(String title, BigDecimal price, Year year) {
        this.title = title;
        this.price = price;
        this.year = year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Year getYear() {
        return year;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("title='").append(title).append('\'');
        sb.append(", price='").append(price).append('\'');
        sb.append(", year='").append(year).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 17;
        if (title != null) {
            result = 31 * result + title.hashCode();
        }
        if (price != null) {
            result = 31 * result + price.hashCode();
        }
        if (year != null) {
            result = 31 * result + year.hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Book))
            return false;
        Book other = (Book)obj;
        boolean titleEquals = (this.title == null && other.title == null)
                || (this.title != null && this.title.equals(other.title));
        return titleEquals && this.price == other.price && this.year == other.year;
    }
}
