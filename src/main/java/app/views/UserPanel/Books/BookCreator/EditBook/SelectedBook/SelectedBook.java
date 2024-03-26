package app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook;

import java.math.BigDecimal;

public class SelectedBook {
    private final String bookName;
    private final String code;
    private final BigDecimal price;
    private final Integer bookId;

    private final Integer categoryId;

    public SelectedBook(Integer bookId, String bookName, String code, BigDecimal price, Integer categoryId) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.code = code;
        this.price = price;
        this.categoryId = categoryId;
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

    public Integer getCategoryId() {
        return categoryId;
    }
}
