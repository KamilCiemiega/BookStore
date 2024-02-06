package app.views;

import app.service.AuthenticationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "UserPanel")
public class UserPanel extends HorizontalLayout implements ViewConfigurator{
    private SideNavigation sideNavigation;
    private final VerticalLayout contentContainer = new VerticalLayout();
    private final AccountSettings accountSettings = new AccountSettings();

    public UserPanel() {
        sideNavigation = new SideNavigation(this);
        configureView();
        contentContainer.addClassName("contentContainer");
        contentContainer.add(accountSettings);
        add(sideNavigation, contentContainer);
    }

    @Override
    public void configureView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
    }

    public void changeView(Component component) {
        contentContainer.removeAll();
        contentContainer.add(component);
    }
}
