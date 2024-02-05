package app.views;

import app.service.AuthenticationService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;

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
        setPadding(false);
        setSpacing(false);
    }

    private VerticalLayout sideNavContainer() {
        VerticalLayout sideNavContainer = new VerticalLayout();
        sideNavContainer.addClassName("sideNav");
        Icon MessageArrowDownIcon = VaadinIcon.CHEVRON_DOWN.create();
        MessageArrowDownIcon.setClassName("arrowDownIcon");
        Icon AccountArrowDownIcon = VaadinIcon.CHEVRON_DOWN.create();
        AccountArrowDownIcon.setClassName("arrowDownIcon");

        HorizontalLayout messageContainer = new HorizontalLayout();
        H1 messageLabel = new H1("Message");
        messageContainer.add(messageLabel, MessageArrowDownIcon);


        Button inboxButton = createButton("Inbox", VaadinIcon.INBOX);
        Button paperplaneButton = createButton("Paperplane", VaadinIcon.PAPERPLANE);
        Button trashButton = createButton("Trash", VaadinIcon.TRASH);

        HorizontalLayout accountContainer = new HorizontalLayout();
        H1 accountLabel = new H1("Account");

        accountContainer.add(accountLabel, AccountArrowDownIcon);

        Button usersButton = createButton("Users", VaadinIcon.GROUP);
        Button permissionsButton = createButton("Permissions", VaadinIcon.KEY);
        Button accountSettingsButton = createButton("Account settings", VaadinIcon.USER);

        sideNavContainer.add(
                messageContainer,
                inboxButton,
                paperplaneButton,
                trashButton,
                accountContainer,
                usersButton,
                permissionsButton,
                accountSettingsButton);

        return sideNavContainer;
    }


    private Button createButton(String label, VaadinIcon icon) {
        Button button = new Button(label, icon.create());
        button.setClassName("sideNavButton");
        switch (label){
            case "Account settings":
                button.addClickListener(e -> UserPanel.changeView(new AccountSettings()));
            break;
            case "Permissions":
            break;
            case "Users":
            default:
               new AccountSettings();

        }
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


