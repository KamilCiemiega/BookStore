package app.views.UserPanel;

import app.service.BookService;
import app.service.CategoryService;
import app.views.UserPanel.Books.BookMainPanel;
import app.views.UserPanel.Category.EditCategory.EditCategoryButton;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "UserPanel")
public class UserPanel extends AppLayout {

    @Autowired
    public UserPanel(BookService bookService, CategoryService categoryService, EditCategoryButton editCategoryButton) {
        DrawerToggle toggle = new DrawerToggle();
        SideNavigation sideNavigation = new SideNavigation();
        BookMainPanel bookPanel = new BookMainPanel(bookService, categoryService, editCategoryButton);
        VerticalLayout contentContainer = new VerticalLayout();
        contentContainer.add(bookPanel);
        contentContainer.addClassName("contentContainer");
        H1 title = new H1("Bookstore");
        title.addClassName("userPanelTitle");


        addToDrawer(sideNavigation);
        addToNavbar(toggle, title);
        setContent(contentContainer);


    }
}
