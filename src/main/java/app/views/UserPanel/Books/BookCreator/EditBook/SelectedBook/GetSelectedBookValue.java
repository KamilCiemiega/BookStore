package app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook;

import app.views.UserPanel.Books.Book;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class GetSelectedBookValue {

    private final Grid<Book> grid;
    public static SelectedBook selectedBook;

    private String bookName;
    private String code;
    private BigDecimal price;
    private Integer bookId;
    private Integer categoryId;
    public static Set<Integer> selectedBookIds = new HashSet<>();
    private Set<Book> previousSelection = new HashSet<>();

    public GetSelectedBookValue(Grid<Book> grid) {
        this.grid = grid;
    }

    public void selectedBookValue(){
        grid.addItemClickListener(e -> {
            bookName = e.getItem().getBookName();
            code = e.getItem().getCode();
            price = e.getItem().getPrice();
            bookId = e.getItem().getId();
            categoryId = e.getItem().getCategoryId();

            selectedBook = new SelectedBook(bookId, bookName, code, price, categoryId);
            UI.getCurrent().getSession().setAttribute("selectedBook", selectedBook);
            UI.getCurrent().navigate("EditBook");
        });
    }

    public static Set<Integer> getSelectedBookIds() {
        return selectedBookIds;
    }

    public void checkBoxSelectedBook(){
        grid.addSelectionListener(e -> {
            Set<Book> currentSelection = e.getAllSelectedItems();

            Set<Book> newlySelected = new HashSet<>(currentSelection);
            newlySelected.removeAll(previousSelection);

            Set<Book> newlyDeselected = new HashSet<>(previousSelection);
            newlyDeselected.removeAll(currentSelection);

            for (Book book : newlyDeselected) {
                Integer bookId = book.getId();
                selectedBookIds.remove(bookId);
            }

            for (Book book : newlySelected) {
                Integer bookId = book.getId();
                selectedBookIds.add(bookId);
            }

            previousSelection = currentSelection;
        });
    }
}
