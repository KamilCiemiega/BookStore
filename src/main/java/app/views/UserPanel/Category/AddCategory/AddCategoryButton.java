package app.views.UserPanel.Category.AddCategory;

import app.views.UserPanel.Utils.AddButton;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

public class AddCategoryButton extends AddButton {

    @Override
    public Button addButton() {
        Button addButton = super.addButton();
        addButton.addClickListener(event -> UI.getCurrent().navigate(AddCategory.class));
        return addButton;
    }
}
