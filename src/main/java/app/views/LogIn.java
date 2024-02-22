package app.views;

import app.service.AuthenticationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@PageTitle("BookStore")
@Route(value = "")
public class LogIn extends VerticalLayout implements ViewConfigurator {
    LoginForm loginForm = new LoginForm();
    private final AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(LogIn.class);
    public LogIn( AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        configureLoginForm();
        configureView();
        add(loginForm, registration());

    }

    @Override
    public void configureView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
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

    private void handleLogin(AbstractLogin.LoginEvent event)  {
        String email = event.getUsername();
        String password = event.getPassword();

        boolean loginSuccessful = authenticationService.authenticateUser(email, password);

        if (loginSuccessful) {
            Notification.show("Login successful", 5000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().navigate("UserPanel");
            logger.info("Authentication successful for user with email: {}", email);
        } else {
            Notification.show("Login failed. Please check your credentials.", 5000, Notification.Position.TOP_CENTER).addThemeVariants(NotificationVariant.LUMO_WARNING);
            loginForm.setEnabled(true);
        }
    }

    private void configureLoginForm() {
        loginForm.addLoginListener(this::handleLogin);
    }

}
