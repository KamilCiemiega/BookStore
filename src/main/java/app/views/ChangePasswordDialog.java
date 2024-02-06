package app.views;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;

public class ChangePasswordDialog extends Dialog {
    public ChangePasswordDialog() {
        setCloseOnEsc(false);

        VerticalLayout verticalLayout = new VerticalLayout();
        PasswordField password = new PasswordField("Password");
        password.addClassName("password");
        password.setRequiredIndicatorVisible(true);

        PasswordField confirmPassword = new PasswordField("Confirm Password");
        confirmPassword.addClassName("confirmPassword");
        confirmPassword.setRequiredIndicatorVisible(true);

        Button saveButton = new Button("Save", event -> {
            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Notification.show("Both password fields are required.").open();
            } else if (!password.getValue().equals(confirmPassword.getValue())) {
                Notification.show("Passwords do not match.").open();
            } else {

                close();
            }
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel", event -> close());


        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);

        verticalLayout.add(password, confirmPassword,buttonLayout);

        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        verticalLayout.setWidth("400px");

        add(verticalLayout );
    }

    private void changePassword(){

    }


}