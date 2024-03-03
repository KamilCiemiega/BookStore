package app.views.UserPanel.Books.BookCreator.EditBook;

import java.math.BigDecimal;

public class SelectedBook {
    private final String bookName;
    private final String code;
    private final BigDecimal price;
    private final Integer bookId;

    public SelectedBook(Integer bookId, String bookName, String code, BigDecimal price) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.code = code;
        this.price = price;
    }

    public Integer getBookId() {
        return bookId;
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
