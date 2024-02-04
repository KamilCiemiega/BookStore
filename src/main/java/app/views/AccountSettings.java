package app.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AccountSettings extends VerticalLayout {
    public AccountSettings() {
        add(newDiv());
    }

    public Button newDiv(){
        Button button = new Button("test");
        button.addClassName("button");
        return button;
    }
}
