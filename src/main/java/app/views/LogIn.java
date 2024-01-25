package app.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("BookStore")
@Route(value = "")
public class LogIn extends VerticalLayout implements ViewConfigurator {
    LoginForm loginForm = new LoginForm();
    public LogIn() {
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

}
