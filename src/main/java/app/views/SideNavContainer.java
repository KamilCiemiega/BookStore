package app.views;

import app.service.AuthenticationService;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;

@Route(value = "SideNavContainer")
public class SideNavContainer extends HorizontalLayout implements ViewConfigurator {

    private String name;
    private String lastName;
    private String email;

    private String sessionId;
  private Map<String, Map<String, String>> loggedInUsers = new HashMap<>();


    public SideNavContainer(AuthenticationService authenticationService ) {
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
        sideNavContainer.setWidth("10%");

        SideNav messagesNav = new SideNav();
        messagesNav.setLabel("Messages");
        messagesNav.setCollapsible(true);
        messagesNav.addItem(new SideNavItem("Inbox", LogIn.class, VaadinIcon.INBOX.create()));
        messagesNav.addItem(new SideNavItem("Sent", LogIn.class, VaadinIcon.PAPERPLANE.create()));
        messagesNav.addItem(new SideNavItem("Trash", LogIn.class, VaadinIcon.TRASH.create()));

        SideNav adminNav = new SideNav();
        adminNav.setLabel(name);
        adminNav.setCollapsible(true);
        adminNav.addItem(new SideNavItem("Account", LogIn.class, VaadinIcon.USER.create()));
        adminNav.addItem(new SideNavItem("Users", LogIn.class, VaadinIcon.GROUP.create()));
        adminNav.addItem(new SideNavItem("Permissions", LogIn.class, VaadinIcon.KEY.create()));

        sideNavContainer.add(messagesNav, adminNav);

        return sideNavContainer;
    }

    private void logInUser(String firstName, String lastName, String email, String sessionId) {
        System.out.println("loginuser" + sessionId);

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


