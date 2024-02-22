package app.views.UserPanel;

import app.service.AccountSettingsService;
import app.service.BookService;
import app.service.UsersService;
import app.views.UserPanel.AccountSettings.AccountSettings;
import app.views.UserPanel.Books.BookPanel;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "UserPanel")
public class UserPanel extends AppLayout implements ViewConfigurator {
    private SideNavigation sideNavigation;
    private final VerticalLayout contentContainer = new VerticalLayout();
    private final BookService bookService;

    public UserPanel(BookService bookService) {
        this.bookService = bookService;
        DrawerToggle toggle = new DrawerToggle();
        sideNavigation = new SideNavigation();
        BookPanel bookPanel = new BookPanel(bookService);
        contentContainer.add(bookPanel);
        contentContainer.addClassName("contentContainer");
        H1 title = new H1("Bookstore");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");


        addToDrawer(sideNavigation);
        addToNavbar(toggle, title);
        setContent(contentContainer);


    }
}
