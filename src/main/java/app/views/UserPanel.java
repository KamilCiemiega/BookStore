package app.views;

import app.service.AuthenticationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "UserPanel")
public class UserPanel extends HorizontalLayout implements ViewConfigurator{
    private final SideNavigation sideNavigation;
    private static final VerticalLayout contentContainer = new VerticalLayout();
    private final AccountSettings accountSettings = new AccountSettings();

    public UserPanel() {
        sideNavigation = new SideNavigation();
        configureView();
        contentContainer.addClassName("contentContainer");
        add(sideNavigation, contentContainer);
        contentContainer.add(accountSettings);
    }

    @Override
    public void configureView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
    }

    public static void changeView(Component component) {
        contentContainer.removeAll();
        contentContainer.add(component);
    }
}
