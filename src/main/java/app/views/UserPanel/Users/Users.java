package app.views.UserPanel.Users;

import app.Model.User;
import app.service.UsersService;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Route(value = "Users", layout = UserPanel.class)
public class Users extends VerticalLayout {

    private final UsersService usersService;
    private User user;

    Integer iterationNumber = 0;
    public Users(UsersService usersService) {
        this.usersService = usersService;
        removeAll();
        setWidthFull();
        setHeight("100px");


        add(Header(), userContainer());
    }

    private Div Header(){
        Div headerContainer = new Div();
        H1 headerName = new H1("Manage Users");
        Button addNewUser = new Button("Add new user");

        headerContainer.add(headerName,addNewUser);

        return headerContainer;
    }
    private TextField createTextField(String label, String value, Integer iterationNumber) {
        if(iterationNumber == 0){
            TextField textField = new TextField();
            textField.setValue(value);
            textField.setEnabled(false);
            return textField;
        }
        TextField textField = new TextField(label);
        textField.setValue(value);
        textField.setEnabled(false);

        return textField;
    }

    private ComboBox<String> createComboBox(String label, List<String> options, String value) {
        ComboBox<String> comboBox = new ComboBox<>(label);
        comboBox.setItems(options);
        comboBox.setValue(value);
        comboBox.setEnabled(false);
        return comboBox;
    }

    private Button createButton(TextField firstNameField, TextField lastNameField,
                                TextField emailField, TextField passwordField,
                                ComboBox<String> comboBox, Integer userId) {
        Button button = new Button("Edit");
        boolean[] modified = {false};

        button.addClickListener(event -> {
            boolean editingMode = !firstNameField.isEnabled();
            if (editingMode) {
                comboBox.setEnabled(true);
                firstNameField.setEnabled(true);
                lastNameField.setEnabled(true);
                emailField.setEnabled(true);
                passwordField.setEnabled(true);
                button.setText("Save");
            } else {
                String firstNameFieldValue = firstNameField.getValue();
                String lastNameFieldValue = lastNameField.getValue();
                String emailFieldValue = emailField.getValue();
                String passwordFieldValue = passwordField.getValue();
                String roleNameValue = comboBox.getValue();
                Integer newRoleNameValue = null;

                if(roleNameValue.equals("User")){
                    newRoleNameValue = 2;
                } else if (roleNameValue.equals("Admin")) {
                    newRoleNameValue = 1;
                }

                if (modified[0]) {
                    if (emailFieldValue.isEmpty() || passwordFieldValue.isEmpty()) {
                        if (emailFieldValue.isEmpty()) {
                            emailField.setInvalid(true);
                            emailField.setErrorMessage("Email cannot be empty");
                        }
                        if (passwordFieldValue.isEmpty()) {
                            passwordField.setInvalid(true);
                            passwordField.setErrorMessage("Password cannot be empty");
                        }
                        return;
                    }
                    try{
                        usersService.updateUserData(firstNameFieldValue, lastNameFieldValue, emailFieldValue, passwordFieldValue,newRoleNameValue,  userId);
                        Notification notification = new Notification("User data updated successfully");
                        notification.setPosition(Notification.Position.TOP_CENTER);
                        notification.addThemeName("success");
                        notification.open();
                    }catch (Exception e){
                        Notification notification = new Notification("An error occurred, while trying to update data");
                        notification.setPosition(Notification.Position.TOP_START);
                        notification.addThemeName("error");
                        notification.open();
                    }

                    modified[0] = false;
                }

                comboBox.setEnabled(false);
                firstNameField.setEnabled(false);
                lastNameField.setEnabled(false);
                emailField.setEnabled(false);
                passwordField.setEnabled(false);
                button.setText("Edit");
            }
        });


        firstNameField.addValueChangeListener(event -> modified[0] = true);
        lastNameField.addValueChangeListener(event -> modified[0] = true);
        emailField.addValueChangeListener(event -> modified[0] = true);
        passwordField.addValueChangeListener(event -> modified[0] = true);

        return button;
    }
    private Div userContainer() {
        Div userContainer = new Div();
        List<User> userList = usersService.getUsers();
        List<String> roleOptions = Arrays.asList("User", "Admin");


        for (User user : userList) {
            Div userRow = new Div();
            iterationNumber ++;

            TextField firstNameField = createTextField("First Name", user.getFirstName(), iterationNumber);
            TextField lastNameField = createTextField("Last Name", user.getLastName(), iterationNumber);
            TextField emailField = createTextField("Email", user.getEmail(), iterationNumber);
            emailField.setRequired(true);
            TextField passwordField = createTextField("Password", user.getPassword(), iterationNumber);
            passwordField.setRequired(true);

            ComboBox<String> comboBox = createComboBox("User role", roleOptions, user.getRoleName());
            Button button = createButton(firstNameField, lastNameField, emailField, passwordField, comboBox, user.getUserId());

            userRow.add(firstNameField, lastNameField, emailField, passwordField, comboBox, button);

            userContainer.add(userRow);
        }

        return userContainer;
    }

}
