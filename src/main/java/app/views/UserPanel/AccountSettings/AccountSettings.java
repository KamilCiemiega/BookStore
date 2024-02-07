package app.views.UserPanel.AccountSettings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AccountSettings extends HorizontalLayout {
    public AccountSettings() {
        setPadding(false);
        setSpacing(false);
        setSizeFull();
        add(changeAccountData());
    }

    private VerticalLayout changeAccountData(){

        VerticalLayout formVerticalLayout = new VerticalLayout();
        formVerticalLayout.addClassName("formVerticalLayout");

        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Name");
        TextField surnameField = new TextField("Surname");
        TextField emailField = new TextField("Email");

        formLayout.setColspan(nameField, 2);
        formLayout.add(nameField);
        formLayout.setColspan(surnameField, 3);
        formLayout.add(surnameField);
        formLayout.setColspan(emailField, 4);
        formLayout.add(emailField);


        Button changePasswordButton = new Button("Change Password");
        changePasswordButton.addClassName("changePasswordButton");
        changePasswordButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST);
           changePasswordButton.addClickListener(event -> {
            // Tutaj dodaj logikę do otwarcia dialogu zmiany hasła
            ChangePasswordOverflow changePasswordOverflow = new ChangePasswordOverflow();
            changePasswordOverflow.open();
        });
        formVerticalLayout.setHorizontalComponentAlignment(Alignment.CENTER, formLayout);
        formVerticalLayout.add(formLayout, changePasswordButton);

        return formVerticalLayout;
    }
}
