package app.views.UserPanel.Utils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

abstract public class AddButton {

    public Button addButton(){
        Button addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addClassName("addButton");
        addButton.getIcon().addClassName("plusIcon");

        return addButton;
    }
}
