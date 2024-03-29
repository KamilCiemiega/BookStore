package app.views.UserPanel.AccountSettings;

import app.service.AccountSettingsService;
import app.service.UserContext;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;

public class ChangePasswordOverflow extends Dialog {

    private final AccountSettingsService accountSettingsService;
    public ChangePasswordOverflow(AccountSettingsService accountSettingsService) {
        this.accountSettingsService = accountSettingsService;
        setCloseOnEsc(false);

        VerticalLayout verticalLayout = new VerticalLayout();
        PasswordField password = new PasswordField("Password");
        password.addClassName("password");
        password.setRequiredIndicatorVisible(true);

        PasswordField confirmPassword = new PasswordField("Confirm Password");
        confirmPassword.addClassName("confirmPassword");
        confirmPassword.setRequiredIndicatorVisible(true);

        Button saveButton = new Button("Save", event -> {
            validatePasswordFields(password, confirmPassword);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel", event -> close());


        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);

        verticalLayout.add(password, confirmPassword,buttonLayout);

        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        verticalLayout.setWidth("400px");

        add(verticalLayout );
    }

    private void validatePasswordFields(PasswordField passwordField, PasswordField confirmPasswordField) {
        Integer userID = UserContext.getUserID();

        String password = passwordField.getValue();
        String confirmPassword = confirmPasswordField.getValue();

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            handleValidationFailure(passwordField, "Both password fields are required.");
            handleValidationFailure(confirmPasswordField, "Both password fields are required.");
        } else if (!password.equals(confirmPassword)) {
            handleValidationFailure(confirmPasswordField, "Passwords do not match.");
        } else {
            accountSettingsService.updateUserPassword(password, userID);
            close();
        }
    }

    private void handleValidationFailure(HasValidation field, String errorMessage) {
        field.setInvalid(true);
        field.setErrorMessage(errorMessage);
    }


}