package app.views.UserPanel.Books;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import java.util.List;

public class BookSearchHandler implements GridRefresher<String>{

    private final ListDataProvider<Book> dataProvider;

    public BookSearchHandler(Grid<Book> grid, List<Book> allBooks) {
        this.dataProvider = new ListDataProvider<>(allBooks);
        grid.setDataProvider(dataProvider);
    }

    public TextField searchField() {
        TextField searchField = new TextField();
        searchField.addClassName("searchField");

        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> refreshGrid(e.getValue()));

        return searchField;
    }

    @Override
    public void refreshGrid(String searchTerm) {
        if (searchTerm == null || searchTerm.isEmpty()) {
            dataProvider.clearFilters();
            return;
        }

        String finalSearchTerm = searchTerm.trim().toLowerCase();
        dataProvider.setFilter(book -> bookMatchesTerm(book, finalSearchTerm));
    }
    private boolean bookMatchesTerm(Book book, String term) {
        return book.getBookName().toLowerCase().contains(term) ||
                book.getCode().toLowerCase().contains(term) ||
                book.getPrice().toString().toLowerCase().contains(term);
    }
}