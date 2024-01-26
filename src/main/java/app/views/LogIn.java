package app.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@PageTitle("BookStore")
@Route(value = "")
public class LogIn extends VerticalLayout implements ViewConfigurator {
    LoginForm loginForm = new LoginForm();
    private final DataSource dataSource;
    public LogIn(DataSource dataSource) {
        this.dataSource = dataSource;
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

    private boolean onLogin(AbstractLogin.LoginEvent event){

        String email = event.getUsername();
        String password = event.getPassword();

        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT password FROM users WHERE email_address = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedPassword = resultSet.getString("password");
                        return password.equals(storedPassword);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return false;
    }
}
