package app.views.UserPanel.Category.EditCategory;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.stereotype.Component;

@Component
public class EditCategoryButton extends VerticalLayout {

    private boolean goToEditClassStatus;


    public EditCategoryButton() {
        add(editButton());
    }

    public Button editButton(){
        Button editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.addClassName("editButton");
        editButton.addClickListener(e -> UI.getCurrent().navigate(EditCategory.class));

        return editButton;
    }

    //    public void setGoToEditClassStatus(boolean goToEditClassStatus) {
//        this.goToEditClassStatus = goToEditClassStatus;
//    }
//
//    public boolean isGoToEditClassStatus() {
//        return goToEditClassStatus;
//    }
}
