package app.views.UserPanel.Users;

import app.Model.User;
import app.service.UsersService;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Route(value = "Users", layout = UserPanel.class)
public class Users extends VerticalLayout {

    private final UsersService usersService;
    private User user;
    public Users(UsersService usersService) {
        this.usersService = usersService;
        removeAll();
        setWidthFull();
        setHeight("100px");


        add(Header(),createUserComponent());
    }

    private Div Header(){
        Div headerContainer = new Div();
        H1 headerName = new H1("Manage Users");
        Button addNewUser = new Button("Add new user");

        headerContainer.add(headerName,addNewUser);

        return headerContainer;
    }

    private Grid<User> createUserComponent() {
            Grid<User> grid = new Grid<>(User.class);
            grid.setItems(usersService.getUsers());

            // Dodaj kolumny do tabeli
            grid.addColumn(User::getFirstName).setHeader("First Name");
            grid.addColumn(User::getLastName).setHeader("Last Name");
            grid.addColumn(User::getEmail).setHeader("Email");
            grid.addColumn(User::getPassword).setHeader("Password");
            grid.addColumn(User::getRoleName).setHeader("Role Name");

            // Dodaj edycję po kliknięciu na wiersz
            grid.addItemClickListener(event -> {
                User user = event.getItem();
                // Dodaj tutaj logikę edycji użytkownika po kliknięciu w wiersz
                System.out.println("Selected user: " + user.getFirstName() + " " + user.getLastName());
            });

            return  grid;

    }
}
