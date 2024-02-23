package app.views.UserPanel.Users;

import app.service.UsersService;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;


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
        Div div = new Div();
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

                validateUserForms(emailFieldValue, passwordFieldValue, emailField, passwordField);
                int newRoleNameValue = roleNameValue.equals("Admin") ? 1 : 2;
                if (!emailField.isInvalid() && !passwordField.isInvalid() && !comboBox.isInvalid()) {
                    if (modified[0]) {
                        try {
                            usersService.updateUserData(firstNameFieldValue, lastNameFieldValue, emailFieldValue, passwordFieldValue, newRoleNameValue, userId);
                            Notification notification = new Notification("User data updated successfully", 3000, Notification.Position.TOP_CENTER);
                            notification.addThemeName("success");
                            notification.open();
                        } catch (Exception e) {
                            Notification notification = new Notification("An error occurred, while trying to update data", 3000, Notification.Position.TOP_CENTER);
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
            iterationNumber ++;
             Div userRow = createUserRow(
                   createTextField("First Name", user.getFirstName(), iterationNumber),
                    createTextField("Last Name", user.getLastName(), iterationNumber),
                    createTextField("Email", user.getEmail(), iterationNumber),
                    createTextField("Password", user.getPassword(), iterationNumber),
                    createComboBox("User role", roleOptions, user.getRoleName(), iterationNumber),
                    user.getUserId()
            );
             userContainer.add(userRow);
        }
        return userContainer;
    }

    private Div createUserRow(TextField firstNameField, TextField lastNameField,
                              TextField emailField, TextField passwordField,
                              ComboBox<String> comboBox, Integer userId) {
        Div userRow = new Div();
        emailField.setRequired(true);
        passwordField.setRequired(true);
        comboBox.setRequired(true);
        comboBox.setAllowCustomValue(false);

        Button editAndSaveButton = createButton(firstNameField, lastNameField, emailField, passwordField, comboBox, userId);
        editAndSaveButton.addClassName("editAndSaveButton");

        userRow.add(firstNameField, lastNameField, emailField, passwordField, comboBox, editAndSaveButton);
        userRow.add(deleteUserRow(userRow, userId));

        return userRow;
    }

    private Button deleteUserRow(Div userRow, Integer userId){
        Button deleteButton = new Button("Delete");
        deleteButton.addClassName("deleteButton");
        deleteButton.addClickListener(event -> {
            try {
                usersService.deleteUser(userId);
                Notification notification = new Notification("User deleted successfully", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.open();
                userRow.setVisible(false);
            } catch (Exception e) {
                Notification notification = new Notification("An error occurred while trying to delete the user", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                logger.error("An error occurred while trying to delete the user", e);
            }
        });
        return deleteButton;
    }
    private Div createEmptyUserRow() {
        Div userRow = new Div();

        int maxUserId = findMaxUserId();
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        emailField.setRequired(true);
        TextField passwordField = new TextField();
        passwordField.setRequired(true);

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setItems(roleOptions);
        comboBox.setValue("User");
        comboBox.setAllowCustomValue(false);
        Button emptyFormButton = createEmptyFormButton(firstNameField, lastNameField, emailField, passwordField, comboBox);
        emptyFormButton.addClassName("emptyFormButton");

        Button deleteButton = new Button("Delete");
        deleteButton.addClassName("deleteButton");
        deleteButton.addClickListener(event -> {
            try {
                if (maxUserId == findMaxUserId()) {
                    userRow.setVisible(false);
                } else {
                    Notification notification = new Notification("User deleted successfully", 3000, Notification.Position.TOP_CENTER);
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    notification.open();
                    userRow.setVisible(false);
                    usersService.deleteUser(findMaxUserId());
                }
            } catch (Exception e) {
                Notification notification = new Notification("An error occurred while trying to delete the user", 3000, Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                notification.open();
                logger.error("An error occurred while trying to delete the user", e);
            }
        });

        userRow.add(firstNameField, lastNameField, emailField, passwordField, comboBox, emptyFormButton, deleteButton);
        return userRow;
    }
    private Button createEmptyFormButton(TextField firstNameField, TextField lastNameField,
                                         TextField emailField, TextField passwordField,
                                         ComboBox<String> comboBox) {
        Button emptyFormButton = new Button("Save");

        emptyFormButton.addClickListener(event -> {
            if ("Save".equals(emptyFormButton.getText())) {

                String firstNameFieldValue = firstNameField.getValue();
                String lastNameFieldValue = lastNameField.getValue();
                String emailFieldValue = emailField.getValue();
                String passwordFieldValue = passwordField.getValue();
                String roleNameValue = comboBox.getValue();

                validateUserForms(emailFieldValue, passwordFieldValue, emailField, passwordField);

                if (!emailField.isInvalid() && !passwordField.isInvalid() && !comboBox.isInvalid()) {
                    int newRoleNameValue = roleNameValue.equals("Admin") ? 1 : 2;

                    try {
                        usersService.insertNewUser(firstNameFieldValue, lastNameFieldValue, emailFieldValue, passwordFieldValue, newRoleNameValue);
                        Notification notification = new Notification("New user added successfully", 3000, Notification.Position.TOP_CENTER);
                        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        notification.open();
                        logger.info("createSaveButton - new user added successfully");

                        emptyFormButton.setText("Edit");
                        firstNameField.setEnabled(false);
                        lastNameField.setEnabled(false);
                        emailField.setEnabled(false);
                        passwordField.setEnabled(false);
                        comboBox.setEnabled(false);

                        findMaxUserId();

                    } catch (Exception e) {
                        Notification notification = new Notification("An error occurred, while trying to update data", 3000, Notification.Position.TOP_CENTER);
                        notification.addThemeVariants(NotificationVariant.LUMO_WARNING);
                        notification.open();
                        logger.error("An error occurred, while trying to update data", e);
                    }
                }
            } else if ("Edit".equals(emptyFormButton.getText())) {
                emptyFormButton.setText("Save");
                firstNameField.setEnabled(true);
                lastNameField.setEnabled(true);
                emailField.setEnabled(true);
                passwordField.setEnabled(true);
                comboBox.setEnabled(true);
            }
        });

        return emptyFormButton;
    }
    private Integer findMaxUserId() {
        try {
            List<User> userList = usersService.getUsers();
            int maxId = Integer.MIN_VALUE;
            for (User user : userList) {
                if (user.getUserId() > maxId) {
                    maxId = user.getUserId();
                }
            }
            return maxId;
        } catch (Exception e) {
            logger.error("An error occurred while trying to find the max user ID", e);
            return null;
        }
    }

    private void validateUserForms(String email, String password, TextField emailField, TextField passwordField){
        if (email.isEmpty() || password.isEmpty()) {
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
        }
    }
}
