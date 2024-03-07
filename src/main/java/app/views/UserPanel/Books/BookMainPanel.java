package app.views.UserPanel.Books;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.AddBook.AddBookButton;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook.GetSelectedBookValue;
import app.views.UserPanel.Books.DeleteBook.DeleteBookButton;
import app.views.UserPanel.UserPanel;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;


import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "BookMainPanel", layout = UserPanel.class)
public class BookMainPanel extends VerticalLayout implements ViewConfigurator {
    private final BookService bookService;
    private final BookSearchHandler searchHandler;
    private final Grid<Book> grid;

    private final DeleteBookButton deleteBookButton;

    public BookMainPanel(BookService bookService) {
        this.bookService = bookService;
        this.grid = allBooksTable();
        this.searchHandler = new BookSearchHandler(grid, bookService.getAllBooks());
        this.deleteBookButton = new DeleteBookButton(bookService);

        configureView();
        add(splitLayout());

        searchHandler.refreshGrid("");
    }

    @Override
    public void configureView() {
        setSizeFull();
        setClassName("BookMainPanel");
    }

    private SplitLayout splitLayout(){
        H2 h2 = new H2("Test header");
        SplitLayout splitLayout = new SplitLayout();
        splitLayout.addClassName("splitLayout");
        splitLayout.addToPrimary(h2);
        splitLayout.addToSecondary(header(), grid);
        splitLayout.setSplitterPosition(20);

        return splitLayout;
    }

    private HorizontalLayout header(){
        HorizontalLayout headerContainer = new HorizontalLayout();
        headerContainer.addClassName("headerContainer");

        headerContainer.add(
                AddBookButton.addBookButton(),
                deleteBookButton.deleteButton(),
                searchHandler.searchField()
        );

        return headerContainer;
    }

    private Grid<Book> allBooksTable(){
        Grid<Book> grid = new Grid<>(Book.class, false);
        grid.addClassName("allBooksGrid");
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        GetSelectedBookValue getSelectedBookValue = new GetSelectedBookValue(grid);
        getSelectedBookValue.selectedBookValue();
        getSelectedBookValue.checkBoxSelectedBook();

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
