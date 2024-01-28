package app.views;

import app.service.AuthenticationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.sql.DataSource;

@PageTitle("BookStore")
@Route(value = "")
public class LogIn extends VerticalLayout implements ViewConfigurator {
    LoginForm loginForm = new LoginForm();
    private final AuthenticationService authenticationService;
    public LogIn( AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        configureLoginForm();
        configureView();
        add(loginForm, registration());

    }

    @Override
    public void configureView() {
        ViewConfigurator.super.configureView();
        addClassName("LogInView");
    }

    private VerticalLayout registration(){
        VerticalLayout registrationContent = new VerticalLayout();
        registrationContent.addClassName("registrationContent");
        registrationContent.setAlignItems(Alignment.CENTER);
        registrationContent.setJustifyContentMode(JustifyContentMode.CENTER);
        registrationContent.setWidth("360px");

        Button RegisterButton = new Button("Register");
        RegisterButton.addClassName("RegistrybButton");
        RegisterButton.addClickListener(event ->
                UI.getCurrent().navigate("Registration")
        );

        Paragraph additionalText = new Paragraph("You don't have account - create new !");
        additionalText.addClassName(LumoUtility.TextAlignment.CENTER);

        registrationContent.add(additionalText,RegisterButton);

        return registrationContent;
    }



    private void handleLogin(AbstractLogin.LoginEvent event) {
        String email = event.getUsername();
        String password = event.getPassword();

        boolean loginSuccessful = authenticationService.authenticateUser(email, password);

        if (loginSuccessful) {
            Notification.show("Login successful", 3000, Notification.Position.TOP_CENTER);
            UI.getCurrent().navigate("UserPanel");
        } else {
            Notification.show("Login failed. Please check your credentials.", 3000, Notification.Position.TOP_CENTER);
            loginForm.setEnabled(true);
        }
    }

    private void configureLoginForm() {
        loginForm.addLoginListener(this::handleLogin);
    }

}
