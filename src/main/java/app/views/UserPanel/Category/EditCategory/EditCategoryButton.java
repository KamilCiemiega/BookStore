package app.views.UserPanel.Category.EditCategory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class EditCategoryButton {

    private boolean focusedElementStatus = true;

    public Button editButton(){
        Button editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.addClassName("editButton");

        editButton.addClickListener(event -> UI.getCurrent().navigate(EditCategory.class));

        return editButton;
    }
}
