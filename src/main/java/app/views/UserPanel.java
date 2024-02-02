package app.views;

import app.service.AuthenticationService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "UserPanel")
public class UserPanel extends VerticalLayout {

    public static VerticalLayout contentContainer;
    public static AccountSettings accountSettings = new AccountSettings();
    public UserPanel(AuthenticationService authenticationService) {
        SideNavigation sideNavContainer = new SideNavigation(authenticationService);
        contentContainer = new VerticalLayout();
        add(sideNavContainer,contentContainer);
    }

    public static void changeView(String nameClass){
        if("Account settings".equals(nameClass)){
            contentContainer.add(accountSettings);
        }


    }
}
