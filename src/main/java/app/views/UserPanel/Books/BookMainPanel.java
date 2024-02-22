package app.views.UserPanel.Books;

import app.service.BookService;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;

import java.util.List;

//@Route(value = "BookPanel", layout = BookPanel.class)
public class BookMainPanel extends VerticalLayout implements ViewConfigurator {
    private final BookService bookService;
    public BookMainPanel(BookService bookService) {
        this.bookService = bookService;
        configureView();
        add(splitLayout());
    }

    @Override
    public void configureView() {
        setSizeFull();
        setClassName("BookMainPanel");
    }

    private SplitLayout splitLayout(){
        H2 h2 = new H2("Test header");
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.addToPrimary(h2);
        splitLayout.addToSecondary(allBooksTable());
        splitLayout.setSplitterPosition(30);

        return splitLayout;
    }

    private Grid<Book> allBooksTable(){
        Grid<Book> grid = new Grid<>(Book.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        grid.addColumn(Book::getBookName).setHeader("Name").setSortable(true).setResizable(true);
        grid.addColumn(Book::getCode).setHeader("Code").setSortable(true)
                        .setWidth("11em").setFlexGrow(0).setResizable(true);
        grid.addColumn(Book::getPrice).setHeader("Price").setSortable(true)
                        .setWidth("11em").setFlexGrow(0).setResizable(true);
        grid.addColumn(Book::getLastUpdate).setHeader("Last Update").setSortable(true)
                        .setWidth("14em").setFlexGrow(0).setResizable(true);

        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

        List<Book> books = bookService.getAllBooks();
        grid.setItems(books);

        return grid;
    }



}
