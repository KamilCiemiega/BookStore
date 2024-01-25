package app.views;

import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Route;

@Route("UserPanel")
public class UserPanel extends HorizontalLayout implements ViewConfigurator {
    public UserPanel() {
        configureView();
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
        sideNavContainer.setWidth("20%");

        SideNav messagesNav = new SideNav();
        messagesNav.setLabel("Messages");
        messagesNav.setCollapsible(true);
        messagesNav.addItem(new SideNavItem("Inbox", LogIn.class, VaadinIcon.INBOX.create()));
        messagesNav.addItem(new SideNavItem("Sent", LogIn.class, VaadinIcon.PAPERPLANE.create()));
        messagesNav.addItem(new SideNavItem("Trash", LogIn.class, VaadinIcon.TRASH.create()));

        SideNav adminNav = new SideNav();
        adminNav.setLabel("Admin");
        adminNav.setCollapsible(true);
        adminNav.addItem(new SideNavItem("Users", LogIn.class, VaadinIcon.GROUP.create()));
        adminNav.addItem(new SideNavItem("Permissions", LogIn.class, VaadinIcon.KEY.create()));

        sideNavContainer.add(messagesNav,adminNav);

        return sideNavContainer;
    }


}
