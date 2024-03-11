package app.views.UserPanel.Books.DeleteBook;

import app.views.UserPanel.Books.Book;
import app.views.UserPanel.Books.GridRefresher;
import com.vaadin.flow.component.grid.Grid;

import java.util.List;

public class BookGridRefresher implements GridRefresher<List<Book>> {
    private final Grid<Book> grid;

    public BookGridRefresher(Grid<Book> grid) {
        this.grid = grid;
    }

    public void refreshGrid(List<Book> updatedBooks) {
        grid.setItems(updatedBooks);
    }
}
