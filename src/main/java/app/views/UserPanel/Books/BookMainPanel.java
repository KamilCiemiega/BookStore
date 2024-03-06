package app.views.UserPanel.Books;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.AddBook.AddBookButton;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook;
import app.views.UserPanel.UserPanel;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;


import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.util.List;

@Route(value = "BookMainPanel", layout = UserPanel.class)
public class BookMainPanel extends VerticalLayout implements ViewConfigurator {
    private final BookService bookService;
    public static SelectedBook selectedBook;
    private final BookSearchHandler searchHandler;
    private final Grid<Book> grid;

    public BookMainPanel(BookService bookService) {
        this.bookService = bookService;
        this.grid = allBooksTable();
        this.searchHandler = new BookSearchHandler(grid, bookService.getAllBooks());

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
        splitLayout.addClassName("splitLayout");
        splitLayout.addToPrimary(h2);
        splitLayout.addToSecondary(header(), grid);
        splitLayout.setSplitterPosition(20);

        return splitLayout;
    }

    private VerticalLayout header(){
        VerticalLayout headerContainer = new VerticalLayout();

        headerContainer.add(searchHandler.searchField());
        headerContainer.add(AddBookButton.addBookButton());

        return headerContainer;
    }

    private Grid<Book> allBooksTable(){
        Grid<Book> grid = new Grid<>(Book.class, false);
        grid.addClassName("allBooksGrid");
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        grid.addItemClickListener(e -> {
            String bookName = e.getItem().getBookName();
            String code = e.getItem().getCode();
            BigDecimal price = e.getItem().getPrice();
            Integer bookId = e.getItem().getId();

            selectedBook = new SelectedBook(bookId, bookName, code, price);
            UI.getCurrent().getSession().setAttribute("selectedBook", selectedBook);
            UI.getCurrent().navigate("EditBook");
        });

        grid.addColumn(Book::getBookName).setHeader("Name").setSortable(true).setResizable(true);
        grid.addColumn(Book::getCode).setHeader("Code").setSortable(true)
                .setWidth("11em").setFlexGrow(0).setResizable(true);
        grid.addColumn(Book::getPrice).setHeader("Price").setSortable(true)
                .setWidth("11em").setFlexGrow(0).setResizable(true);
        grid.addColumn(Book::getLastUpdate).setHeader("Last Update").setSortable(true)
                .setWidth("14em").setFlexGrow(0).setResizable(true);

        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

        List<Book> books = bookService.getAllBooks();
        searchHandler.refreshGrid("");
        grid.setItems(books);

        return grid;
    }
}
