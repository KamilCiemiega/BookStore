package app.views.UserPanel.Books;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.BookCreator;
import app.views.UserPanel.Books.BookCreator.SelectedBook;
import app.views.UserPanel.UserPanel;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.util.List;

@Route(value = "BookMainPanel", layout = UserPanel.class)
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
        splitLayout.addClassName("splitLayout");
        splitLayout.addToPrimary(h2);
        splitLayout.addToSecondary(header(), allBooksTable());
        splitLayout.setSplitterPosition(20);

        return splitLayout;
    }

    private VerticalLayout header(){
        VerticalLayout headerContainer = new VerticalLayout();

        Button addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addClickListener(event -> UI.getCurrent().navigate(BookCreator.class));
        addButton.addClassName("addButton");
        addButton.getIcon().addClassName("plusIcon");

        headerContainer.add(addButton);

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

            SelectedBook selectedBook = new SelectedBook(bookName, code, price);
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
        grid.setItems(books);

        return grid;
    }


}
