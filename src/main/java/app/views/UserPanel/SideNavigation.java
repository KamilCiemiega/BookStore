package app.views.UserPanel;

import app.service.AccountSettingsService;
import app.service.UserContext;
import app.service.UsersService;
import app.views.UserPanel.AccountSettings.AccountSettings;
import app.views.UserPanel.Books.BookMainPanel;
import app.views.UserPanel.Users.Users;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

import java.util.HashMap;
import java.util.Map;


@Route(value = "SideNavigation")
public class SideNavigation extends HorizontalLayout implements ViewConfigurator {
    private Map<String, Map<String, String>> loggedInUsers = new HashMap<>();

    public SideNavigation() {
        configureView();
        logInUserData();
        add(sideNavContainer());

    }

    @Override
    public void configureView() {
        setPadding(false);
        setSpacing(false);
    }

    private VerticalLayout sideNavContainer() {
        VerticalLayout sideNavContainer = new VerticalLayout();
        sideNavContainer.addClassName("sideNav");

        Icon arrowDownIconAccount = createArrowDownIcon();
        Icon arrowDownIconBooks = createArrowDownIcon();


        HorizontalLayout booksContainer = new HorizontalLayout();
        H1 booksLabel = new H1("Books");

        booksContainer.add(booksLabel, arrowDownIconBooks);

        RouterLink bookListLink = new RouterLink();
        Button bookListButton = createNavigationButton("Books list", BookMainPanel.class, VaadinIcon.BOOK);
        bookListLink.add(bookListButton);

        HorizontalLayout accountContainer = new HorizontalLayout();
        H1 accountLabel = new H1("Account");

        accountContainer.add(accountLabel, arrowDownIconAccount);

        RouterLink usersLink = new RouterLink();
        Button usersButton = createNavigationButton("Users", Users.class, VaadinIcon.GROUP);
        usersLink.add(usersButton);

        RouterLink accountSettingsLink = new RouterLink();
        Button accountSettingsButton = createNavigationButton("accountSettings", AccountSettings.class, VaadinIcon.GROUP);
        accountSettingsLink.add(accountSettingsButton);

        sideNavContainer.add(
                booksContainer,
                bookListLink,
                accountContainer,
                usersLink,
                accountSettingsLink
                );

        return sideNavContainer;
    }
    private Icon createArrowDownIcon() {
        Icon arrowDownIcon = VaadinIcon.CHEVRON_DOWN.create();
        arrowDownIcon.setClassName("arrowDownIcon");
        return arrowDownIcon;
    }
    private Button createNavigationButton(String label, Class<? extends Component> targetClass, VaadinIcon icon) {
        Button button = new Button(label, icon.create());
        button.setClassName("sideNavButton");
        button.addClickListener(e -> getUI().ifPresent(ui -> ui.navigate(targetClass)));
        return button;
    }

    private void logInUserData() {
        String firstName = UserContext.getFirstName();
        String lastName = UserContext.getLastName();
        String email = UserContext.getEmail();
        String sessionId = UserContext.getSesionId();

        Map<String, String> userAttributes = new HashMap<>();
        if (sessionId != null && firstName != null && lastName != null && email != null) {
            userAttributes.put("firstName", firstName);
            userAttributes.put("lastName", lastName);
            userAttributes.put("email", email);

            loggedInUsers.put(sessionId, userAttributes);
        }
    }

    private void removeUser() {
        String sessionId = UserContext.getSesionId();
        loggedInUsers.remove(sessionId);
    }
}


