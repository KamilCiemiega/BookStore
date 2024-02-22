package app.views.UserPanel;

import app.service.BookService;
import app.views.UserPanel.Books.BookMainPanel;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "UserPanel")
public class UserPanel extends AppLayout {
    private SideNavigation sideNavigation;
    private final VerticalLayout contentContainer = new VerticalLayout();
    private final BookService bookService;

    public UserPanel(BookService bookService) {
        this.bookService = bookService;
        DrawerToggle toggle = new DrawerToggle();
        sideNavigation = new SideNavigation();
        BookMainPanel bookPanel = new BookMainPanel(bookService);
        contentContainer.add(bookPanel);
        contentContainer.addClassName("contentContainer");
        H1 title = new H1("Bookstore");
        title.addClassName("userPanelTitle");


        addToDrawer(sideNavigation);
        addToNavbar(toggle, title);
        setContent(contentContainer);


    }
}
