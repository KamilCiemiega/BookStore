package app.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.awt.*;

public class AccountSettings extends HorizontalLayout implements ViewConfigurator {
    public AccountSettings() {
        configureView();
        add(changeAccountData());
    }

    @Override
    public void configureView() {
        ViewConfigurator.super.configureView();
        setPadding(false);
        setSpacing(false);
    }

    private HorizontalLayout changeAccountData(){

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Name");
        TextField surnameField = new TextField("Surname");
        PasswordField passwordField = new PasswordField("Password");
        TextField emailField = new TextField("Email");

        formLayout.setColspan(nameField, 2);
        formLayout.add(nameField);
        formLayout.setColspan(surnameField, 3);
        formLayout.add(surnameField);
        formLayout.setColspan(emailField, 4);
        formLayout.add(emailField);
        formLayout.setColspan(passwordField, 5);
        formLayout.add(passwordField);

//        Button changePasswordButton = new Button("Change Password");
//        changePasswordButton.addClickListener(event -> {
//            // Tutaj dodaj logikę do otwarcia dialogu zmiany hasła
//            ChangePasswordDialog changePasswordDialog = new ChangePasswordDialog();
//            changePasswordDialog.open();
//        });

        horizontalLayout.add(formLayout);

        return horizontalLayout;
    }
}
