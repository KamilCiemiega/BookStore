package app.views.UserPanel.Utils;

import com.vaadin.flow.component.textfield.TextField;

public class HiddenTextField {
    public static TextField createHiddenTextField(){
        TextField hiddenTextField = new TextField();
        hiddenTextField.setVisible(false);
        return hiddenTextField;
    }
}
