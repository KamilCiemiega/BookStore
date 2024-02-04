package app.views;

import app.service.AuthenticationService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "UserPanel")
public class UserPanel extends HorizontalLayout {
    private final SideNavigation sideNavigation;
    public static VerticalLayout contentContainer;

    public UserPanel(AuthenticationService authenticationService) {
        sideNavigation = new SideNavigation(authenticationService);
        contentContainer = new VerticalLayout();
        contentContainer.setSizeFull();
        configureUserPanelView();
        add(sideNavigation);
    }

    private void configureUserPanelView(){
        setSizeFull();
        setPadding(false);
        setSpacing(false);
    }

    public static void changeView(Component component) {
        contentContainer.removeAll();
        contentContainer.add(component);
    }




}
