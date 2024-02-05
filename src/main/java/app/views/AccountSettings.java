package app.views;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

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

    private FormLayout changeAccountData(){
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Name");
        TextField surnameField = new TextField("Surname");
        PasswordField passwordField = new PasswordField("Password");
        TextField emailField = new TextField("Email");

        formLayout.setColspan(nameField, 2);
        formLayout.add(nameField);
        formLayout.setColspan(surnameField, 3);
        formLayout.add(surnameField);
        formLayout.setColspan(passwordField, 4);
        formLayout.add(passwordField);
        formLayout.setColspan(emailField, 5);
        formLayout.add(emailField);

        return formLayout;
    }
}
