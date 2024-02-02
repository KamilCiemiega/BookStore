package app.views;

import app.service.AuthenticationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;
import org.postgresql.util.LruCache;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Route(value = "SideNavContainer")
public class SideNavigation extends HorizontalLayout implements ViewConfigurator {

    private final String name;
    private final String lastName;
    private final String email;

    private final String sessionId;
  private Map<String, Map<String, String>> loggedInUsers = new HashMap<>();


    public SideNavigation(AuthenticationService authenticationService ) {
        this.name = authenticationService.getFirstName();
        this.lastName = authenticationService.getLastName();
        this.email = authenticationService.getEmail();
        this.sessionId = authenticationService.getSessionId();
        configureView();
        logInUser(name, lastName,email,sessionId);
        add(sideNavContainer());

    }

    @Override
    public void configureView() {
        ViewConfigurator.super.configureView();
        getElement().getStyle().set("align-items", "");
        getElement().getStyle().set("justify-content", "");
    }

    private VerticalLayout sideNavContainer() {
        VerticalLayout sideNavContainer = new VerticalLayout();
        sideNavContainer.addClassName("sideNav");
        sideNavContainer.setWidth("12%");


        SideNav messagesNav = new SideNav();
        messagesNav.setLabel("Messages");
        messagesNav.setCollapsible(true);
        messagesNav.addItem(new SideNavItem("Inbox", LogIn.class, VaadinIcon.INBOX.create()));
        messagesNav.addItem(new SideNavItem("Sent", LogIn.class, VaadinIcon.PAPERPLANE.create()));
        messagesNav.addItem(new SideNavItem("Trash", LogIn.class, VaadinIcon.TRASH.create()));


        Button accountSettingsButton = createButton("Account settings", VaadinIcon.USER);
        Button usersButton = createButton("Users", VaadinIcon.GROUP);
        Button permissionsButton = createButton("Permissions", VaadinIcon.KEY);



        sideNavContainer.add(messagesNav,  createItem("Account"), accountSettingsButton, usersButton, permissionsButton);

        return sideNavContainer;
    }


    private Div createItem(String label) {
        HorizontalLayout itemLayout = new HorizontalLayout();
        itemLayout.setAlignItems(Alignment.CENTER);

        Icon arrowDownIcon = VaadinIcon.CHEVRON_DOWN.create();
        arrowDownIcon.setSize("12px");
        itemLayout.add(arrowDownIcon, new Text(label));

        return new Div(itemLayout);
    }
    private Button createButton(String label, VaadinIcon icon) {
        Button button = new Button(label, icon.create());
        button.setClassName("sideNavButton");
        button.addClickListener(e -> UserPanel.changeView(label));
        return button;
    }
    private void logInUser(String firstName, String lastName, String email, String sessionId) {

        Map<String, String> userAttributes = new HashMap<>();
        if (sessionId != null && firstName != null && lastName != null && email != null) {
            userAttributes.put("firstName", firstName);
            userAttributes.put("lastName", lastName);
            userAttributes.put("email", email);

            loggedInUsers.put(sessionId, userAttributes);
        }
    }

    private void removeUser(String sessionId) {
        loggedInUsers.remove(sessionId);
    }
}


