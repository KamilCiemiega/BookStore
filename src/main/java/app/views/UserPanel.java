package app.views;

import app.service.AuthenticationService;
import com.github.javaparser.resolution.model.Value;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "UserPanel")
public class UserPanel extends VerticalLayout {

    public UserPanel(AuthenticationService authenticationService) {
        SideNavContainer sideNavContainer = new SideNavContainer(authenticationService);
        add(sideNavContainer);
    }

}
