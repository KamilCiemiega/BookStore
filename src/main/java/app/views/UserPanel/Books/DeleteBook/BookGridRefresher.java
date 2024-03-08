package app.views.UserPanel.Books.DeleteBook;

import app.views.UserPanel.Books.Book;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.List;

public class BookGridRefresher {
    private final Grid<Book> grid;

    public BookGridRefresher(Grid<Book> grid) {
        this.grid = grid;
    }

    public void refreshGrid(List<Book> updatedBooks) {
        grid.setItems(updatedBooks);
    }
}
