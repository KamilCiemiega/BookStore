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
import com.vaadin.flow.server.SessionExpiredException;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

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



    private void handleLogin(AbstractLogin.LoginEvent event) throws SessionExpiredException {

            String email = event.getUsername();
            String password = event.getPassword();

            boolean loginSuccessful = authenticationService.authenticateUser(email, password);

            if (loginSuccessful) {
                VaadinRequest currentRequest = VaadinRequest.getCurrent();
                VaadinSession vaadinSession = null;

                try {
                    vaadinSession = currentRequest.getService().findVaadinSession(currentRequest);
//                    String sessionId = vaadinSession.getSession().getId();
                    int sessionTimeoutInSeconds = vaadinSession.getSession().getMaxInactiveInterval();
                    System.out.println("Czas trwania sesji: " + sessionTimeoutInSeconds + " sekundy");
                } catch (SessionExpiredException e) {
                    // Obsługa przypadku, gdy sesja jest wygasła
                    // Na przykład, przekieruj użytkownika na stronę logowania
                    // lub pokaż komunikat o wygaśnięciu sesji
                    Notification.show("Your session has expired. Please log in again.", 3000, Notification.Position.MIDDLE);
                    // Przekieruj na stronę logowania
                    UI.getCurrent().navigate("login");
                    logger.info("Session expired");
                }

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
