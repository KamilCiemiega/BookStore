package app.views.UserPanel.Books;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Book {
    private int id;
    private String bookName;
    private String code;
    private BigDecimal price;
    private Timestamp lastUpdate;

    public Book(int id, String bookName, String code, BigDecimal price, Timestamp lastUpdate) {
        this.id = id;
        this.bookName = bookName;
        this.code = code;
        this.price = price;
        this.lastUpdate = lastUpdate;
    }

    public int getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", code='" + code + '\'' +
                ", price=" + price +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
