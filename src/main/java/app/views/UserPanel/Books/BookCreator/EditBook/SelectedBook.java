package app.views.UserPanel.Books.BookCreator.EditBook;

import java.math.BigDecimal;

public class SelectedBook {
    private String bookName;
    private String code;
    private BigDecimal price;

    public SelectedBook(String bookName, String code, BigDecimal price) {
        this.bookName = bookName;
        this.code = code;
        this.price = price;
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
}
