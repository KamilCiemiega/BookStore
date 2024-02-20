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
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Route(value = "Users", layout = UserPanel.class)
public class Users extends VerticalLayout {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);
    private final UsersService usersService;
    private User user;
    List<String> roleOptions = Arrays.asList("User", "Admin");

    Integer iterationNumber = 0;
    public Users(UsersService usersService) {
        this.usersService = usersService;
        removeAll();
        usersViewStyle();
        add(Header(), userContainer());
    }

    private void usersViewStyle() {
        setSizeFull();
        getStyle().set("height", "100%");
        setAlignItems(Alignment.CENTER);
    }
    private TextField createTextField(String label, String value, Integer iterationNumber) {
        if(iterationNumber == 1){
            TextField textField = new TextField(label);
            textField.setValue(value);
            textField.setEnabled(false);
            return textField;
        }
        TextField textField = new TextField();
        textField.setValue(value);
        textField.setEnabled(false);

        return textField;
    }
    private H1 Header(){
        H1 userHeader = new H1("Manage Users");
        userHeader.addClassName("userHeader");
        return userHeader;
    }

    private ComboBox<String> createComboBox(String label, List<String> options, String value, Integer iterationNumber) {
        if(iterationNumber == 1){
            ComboBox<String> comboBox = new ComboBox<>(label);
            comboBox.setItems(options);
            comboBox.setValue(value);
            comboBox.setEnabled(false);
            return comboBox;
        }
        ComboBox<String> comboBox = new ComboBox<>();
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
                int newRoleNameValue = roleNameValue.equals("Admin") ? 1 : 2;

                if (modified[0]) {
                    if (emailFieldValue.isEmpty() || passwordFieldValue.isEmpty()) {
                        if (emailFieldValue.isEmpty()) {
                            emailField.setInvalid(true);
                            emailField.setErrorMessage("Email cannot be empty");
                            logger.info("createButton Empty email field");
                        }
                        if (passwordFieldValue.isEmpty()) {
                            passwordField.setInvalid(true);
                            passwordField.setErrorMessage("Password cannot be empty");
                            logger.info("createButton Empty password field");
                        }
                        return;
                    }
                    try{
                        usersService.updateUserData(firstNameFieldValue, lastNameFieldValue, emailFieldValue, passwordFieldValue,newRoleNameValue,  userId);
                        Notification notification = new Notification("User data updated successfully", 3000, Notification.Position.TOP_CENTER);
                        notification.addThemeName("success");
                        notification.open();
                    }catch (Exception e){
                        Notification notification = new Notification("An error occurred, while trying to update data", 3000,Notification.Position.TOP_CENTER);
                        notification.addThemeName("error");
                        notification.open();
                        logger.error("An error occurred, while trying to update data", e);
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
        comboBox.addValueChangeListener(event -> modified[0] = true);

        return button;
    }
    private Div userContainer() {
        Div userContainer = new Div();
        userContainer.addClassName("userContainer");
        List<User> userList = usersService.getUsers();
        Button addNewUser = new Button("Add new user");
        addNewUser.addClassName("addNewUser");
        Icon plusIcon = new Icon(VaadinIcon.USER_STAR);
        addNewUser.setIcon(plusIcon);
        userContainer.add(addNewUser);
        addNewUser.addClickListener(e -> userContainer.add(createEmptyUserRow()));

        for (User user : userList) {
            Div userRow = new Div();
            iterationNumber ++;

            TextField firstNameField = createTextField("First Name", user.getFirstName(), iterationNumber);
            TextField lastNameField = createTextField("Last Name", user.getLastName(), iterationNumber);
            TextField emailField = createTextField("Email", user.getEmail(), iterationNumber);
            emailField.setRequired(true);
            TextField passwordField = createTextField("Password", user.getPassword(), iterationNumber);
            passwordField.setRequired(true);

            ComboBox<String> comboBox = createComboBox("User role", roleOptions, user.getRoleName(),iterationNumber);
            Button button = createButton(firstNameField, lastNameField, emailField, passwordField, comboBox, user.getUserId());
            button.addClassName("button");

            userRow.add(firstNameField, lastNameField, emailField, passwordField, comboBox, button);

            userContainer.add(userRow);
        }
        return userContainer;
    }
    private Div createEmptyUserRow() {
        Div userRow = new Div();

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        emailField.setRequired(true);
        TextField passwordField = new TextField();
        passwordField.setRequired(true);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(roleOptions);
        Button saveButton = createSaveButton(firstNameField, lastNameField, emailField, passwordField, comboBox);
        saveButton.addClassName("saveButton");
        userRow.add(firstNameField, lastNameField, emailField, passwordField, comboBox, saveButton);
        return userRow;
    }
    private Button createSaveButton(TextField firstNameField, TextField lastNameField,
                                    TextField emailField, TextField passwordField,
                                    ComboBox<String> comboBox) {
        Button saveButton = new Button("Save");

        saveButton.addClickListener(event -> {
            System.out.println(event);
            String firstName = firstNameField.getValue();
            String lastName = lastNameField.getValue();
            String email = emailField.getValue();
            String password = passwordField.getValue();
            String roleName = comboBox.getValue();

            if (email.isEmpty() || password.isEmpty() || comboBox.isEmpty()) {
                if (email.isEmpty()) {
                    emailField.setInvalid(true);
                    emailField.setErrorMessage("Email cannot be empty");
                    logger.info("createSaveButton Empty email field");
                }
                if (password.isEmpty()) {
                    passwordField.setInvalid(true);
                    passwordField.setErrorMessage("Password cannot be empty");
                    logger.info("createSaveButton Empty password field");
                }
                if (comboBox.isEmpty()){
                    comboBox.setInvalid(true);
                    comboBox.setErrorMessage("Role cannot be empty");
                    logger.info("Empty role name");
                }
                return;

            }
            int newRoleNameValue = roleName.equals("Admin") ? 1 : 2;
            try{
                usersService.insertNewUser(firstName, lastName, email, password, newRoleNameValue);
                Notification notification = new Notification("New user added successfully", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.open();
                logger.info("createSaveButton - new user added successfully");
            } catch (Exception e){
                Notification notification = new Notification("An error occurred, while trying to update data", 3000,Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                notification.open();
                logger.error("An error occurred, while trying to update data", e);
            }
        });

        return saveButton;
    }


}
