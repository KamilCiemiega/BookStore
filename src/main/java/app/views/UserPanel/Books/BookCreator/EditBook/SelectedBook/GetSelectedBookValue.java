package app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook;

import app.service.BookService;
import app.views.UserPanel.Books.Book;
import app.views.UserPanel.Books.DeleteBook.DeleteBook;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;

import java.math.BigDecimal;
import java.util.Set;

public class GetSelectedBookValue {

    private final Grid<Book> grid;
    public static SelectedBook selectedBook;

    private String bookName;
    private String code;
    private BigDecimal price;
    private Integer bookId;
    private final DeleteBook deleteBook;

    public GetSelectedBookValue(BookService bookService, Grid<Book> grid) {
        this.grid = grid;
        this.deleteBook = new DeleteBook(bookService);
    }

    public void selectedBookValue(){
        grid.addItemClickListener(e -> {
            bookName = e.getItem().getBookName();
            code = e.getItem().getCode();
            price = e.getItem().getPrice();
            bookId = e.getItem().getId();

            selectedBook = new SelectedBook(bookId, bookName, code, price);
            UI.getCurrent().getSession().setAttribute("selectedBook", selectedBook);
            UI.getCurrent().navigate("EditBook");
        });
    }

    public void checkBoxSelectedBook(){
        grid.addSelectionListener(e -> {
            Set<Book> selectedBooks = e.getAllSelectedItems();
            if (selectedBooks != null){
                for (Book book : selectedBooks) {
                    Integer bookId = book.getId();
                    deleteBook.addBookToList(bookId);
                }
            }
        });

    }
}
