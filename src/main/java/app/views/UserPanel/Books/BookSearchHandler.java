package app.views.UserPanel.Books;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.util.List;
import java.util.stream.Collectors;

public class BookSearchHandler {

    private final Grid<Book> grid;
    private final List<Book> allBooks;
    private final ListDataProvider<Book> dataProvider;

    public BookSearchHandler(Grid<Book> grid, List<Book> allBooks) {
        this.grid = grid;
        this.allBooks = allBooks;
        this.dataProvider = new ListDataProvider<>(allBooks);
        this.grid.setDataProvider(dataProvider);
    }

    public TextField searchField() {
        TextField searchField = new TextField();

        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> refreshGrid(e.getValue()));

        return searchField;
    }

    public void refreshGrid(String searchTerm) {
        final String finalSearchTerm = searchTerm.trim().toLowerCase();
        List<Book> filteredBooks = allBooks.stream()
                .filter(book -> bookMatchesTerm(book, searchTerm))
                .collect(Collectors.toList());

        dataProvider.getItems().clear(); // Usuń istniejące elementy
        dataProvider.getItems().addAll(filteredBooks); // Dodaj nowe elementy
        dataProvider.refreshAll(); // Odśwież widok
    }
    private boolean bookMatchesTerm(Book book, String term) {
        return book.getBookName().toLowerCase().contains(term) ||
                book.getCode().toLowerCase().contains(term) ||
                book.getPrice().toString().toLowerCase().contains(term);
    }
}