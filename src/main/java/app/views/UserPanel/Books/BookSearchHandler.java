//package app.views.UserPanel.Books;
//
//import com.vaadin.flow.component.grid.Grid;
//import com.vaadin.flow.component.grid.GridVariant;
//import com.vaadin.flow.component.icon.VaadinIcon;
//import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
//import com.vaadin.flow.data.value.ValueChangeMode;
//import com.vaadin.flow.shared.Registration;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//
//public class BookSearchHandler {
//
//    private final Grid<Book> grid;
//    private final List<Book> allBooks;
//
//    public BookSearchHandler(Grid<Book> grid, List<Book> allBooks) {
//        this.grid = grid;
//        this.allBooks = allBooks;
//    }
//
//    public void setupSearchField(TextField searchField) {
//        searchField.setWidth("50%");
//        searchField.setPlaceholder("Search");
//        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
//        searchField.setValueChangeMode(ValueChangeMode.EAGER);
//        searchField.addValueChangeListener(e -> refreshGrid());
//    }
//
//    public void addSearchFilter(TextField searchField) {
//        grid.setItems(allBooks);
//
//        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
//
//        dataView.addFilter(book -> {
//            String searchTerm = searchField.getValue().trim();
//
//            if (searchTerm.isEmpty())
//                return true;
//
//            boolean matchesName = matchesTerm(book.getBookName(), searchTerm);
//            boolean matchesCode = matchesTerm(book.getCode(), searchTerm);
//            boolean matchesPrice = matchesTerm(book.getPrice().toString(), searchTerm);
//            boolean matchesLastUpdate = matchesTerm(formatLastUpdate(book.getLastUpdate()), searchTerm);
//
//            return matchesName || matchesCode || matchesPrice || matchesLastUpdate;
//        });
//    }
//
//    private boolean matchesTerm(String value, String term) {
//        return value.toLowerCase().contains(term.toLowerCase());
//    }
//
//    private String formatLastUpdate(LocalDateTime lastUpdate) {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        return lastUpdate.format(formatter);
//    }
//
//    public Registration addSearchListener(ComponentEventListener<SearchBox.SearchEvent> listener) {
//        return searchField.addListener(SearchBox.SearchEvent.class, listener);
//    }
//
//    public void refreshGrid() {
//        grid.getDataProvider().refreshAll();
//    }
//}