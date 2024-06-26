package app.views;

import app.service.AuthenticationService;
import app.views.UserPanel.Utils.ShowNotification;
import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Route("Registration")
public class Registry extends VerticalLayout implements ViewConfigurator {

    private final AuthenticationService  authenticationService ;

    private H1 header = new H1("Complete the provided fields");
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private EmailField email = new EmailField("Email");
    private PasswordField password = new PasswordField("Password");
    private PasswordField confirmPassword = new PasswordField("Confirm Password");

    public Registry(AuthenticationService authenticationService ) {
        this.authenticationService = authenticationService;
        configureView();

        add(header, registerFields(), registration());
    }

    @Override
    public void configureView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        addClassName("RegistryView");
    }

    private VerticalLayout registerFields() {
        VerticalLayout registerFieldsContainer = new VerticalLayout();
        registerFieldsContainer.addClassName("registerFields");
        registerFieldsContainer.setAlignItems(Alignment.CENTER);
        registerFieldsContainer.setJustifyContentMode(JustifyContentMode.CENTER);
        registerFieldsContainer.setWidth("360px");
        header.getStyle().set("color", "white");
        email.getElement().getStyle().set("margin", "0");

        registerFieldsContainer.add(firstName, lastName, email, password, confirmPassword);

        return registerFieldsContainer;
    }

    private VerticalLayout registration() {
        VerticalLayout registrationContent = new VerticalLayout();
        registrationContent.addClassName("registrationContent");
        registrationContent.setAlignItems(Alignment.CENTER);
        registrationContent.setJustifyContentMode(JustifyContentMode.CENTER);
        registrationContent.setWidth("360px");

        Button registerButton = new Button("Register");
        registerButton.addClassName("RegistryButton");
        registerButton.addClickListener(event -> validateAndSave());

        Paragraph additionalText = new Paragraph("You got the account LogIn !");
        additionalText.addClassName(LumoUtility.TextAlignment.CENTER);

        Button logInButton = new Button("LogIn");
        logInButton.addClassName("logInButton");
        logInButton.addClickListener(event ->
                UI.getCurrent().navigate("")
        );

        HorizontalLayout backToLogInPage = new HorizontalLayout(additionalText, logInButton);
        backToLogInPage.addClassName("backToLogInPage");
        registrationContent.add(registerButton, backToLogInPage);

        return registrationContent;
    }

    private void validateAndSave() {
        clearValidationErrors();

        String firstNameValue = firstName.getValue();
        String lastNameValue = lastName.getValue();
        String emailValue = email.getValue();
        String passwordValue = password.getValue();
        String confirmPasswordValue = confirmPassword.getValue();

        if (isEmpty(firstNameValue)) {
            handleValidationFailure(firstName, "First name cannot be empty");
        }

        if (isEmpty(lastNameValue)) {
            handleValidationFailure(lastName, "Last name cannot be empty");
        }

        if (!isValidEmail(emailValue)) {
            handleValidationFailure(email, "Invalid email format");
        }

        if (isEmpty(passwordValue)) {
            handleValidationFailure(password, "Password cannot be empty");
        }
        if (isEmpty(confirmPasswordValue)) {
            handleValidationFailure(confirmPassword, "Confirm password cannot be empty");
        }

        if (!isValidPassword(passwordValue, confirmPasswordValue)) {
            handleValidationFailure(confirmPassword, "Passwords do not match");
        }
        if (areAllFieldsValid()) {
            boolean registrationSuccess =  authenticationService.insertData(firstNameValue, lastNameValue, passwordValue, emailValue);
            if (registrationSuccess) {
                ShowNotification.showNotification("Registration successful!", NotificationVariant.LUMO_SUCCESS);
                UI.getCurrent().navigate("");
            }
        }
    }

    private void handleValidationFailure(HasValidation field, String errorMessage) {
        field.setInvalid(true);
        field.setErrorMessage(errorMessage);
    }

    private void clearValidationErrors() {
        HasValidation[] fields = {firstName, lastName, email, password, confirmPassword};
        for (HasValidation field : fields) {
            field.setInvalid(false);
            field.setErrorMessage(null);
        }
    }

    private boolean isEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    private boolean isValidEmail(String email) {
        return !isEmpty(email) && email.contains("@");
    }

    private boolean isValidPassword(String password, String confirmPassword) {
        return  password.equals(confirmPassword);
    }

    private boolean areAllFieldsValid() {
        HasValidation[] fields = {firstName, lastName, email, password, confirmPassword};
        for (HasValidation field : fields) {
            if (field.isInvalid()) {
                return false;
            }
        }
        return true;
    }
}