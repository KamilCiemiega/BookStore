package app.views.UserPanel.Users;

import app.service.UsersService;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
@Component
public class Users extends VerticalLayout {

//    @Autowired
//    private UsersService usersService;

    public Users() {
        removeAll();
        setWidthFull();
        setHeight("100px");

        add(Header(), createUserComponent());
    }

    private Div Header(){
        Div headerContainer = new Div();
        H1 headerName = new H1("Manage Users");
        Button addNewUser = new Button("Add new user");

        headerContainer.add(headerName,addNewUser);

        return headerContainer;
    }

    private HorizontalLayout createUserComponent() {
        HorizontalLayout userLayout = new HorizontalLayout();
//        usersService.getUsers();

        return userLayout;
    }
}
