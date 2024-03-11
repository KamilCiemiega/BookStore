package app.views.UserPanel.Utils;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

public class BackToMainButton {
    public static Button backToMainButton(){
        Button backButton = new Button("Back");
        backButton.addClassName("backButton");
        backButton.addClickListener(e -> UI.getCurrent().navigate("BookMainPanel"));

        return backButton;
    }
}
