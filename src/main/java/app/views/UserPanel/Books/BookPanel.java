package app.views.UserPanel.Books;

import app.service.BookService;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

//@Route(value = "BookPanel", layout = BookPanel.class)
public class BookPanel extends VerticalLayout {
    private final BookService bookService;
    public BookPanel(BookService bookService) {
        this.bookService = bookService;
        add(test());
    }

    private H1 test() {
        H1 h1 = new H1("test");
        return h1;
    }

}
