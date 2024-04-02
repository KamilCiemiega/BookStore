package app.views.UserPanel.Category.EditCategory;

import app.views.UserPanel.Category.CategoryData;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class EditCategoryButton extends VerticalLayout {

    private boolean goToEditClassStatus;
    private CategoryData lastCategory;

    public EditCategoryButton() {
        add(editButton());
    }

    public Button editButton(){
        Button editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.addClassName("editButton");
        editButton.addClickListener(e -> UI.getCurrent().navigate(EditCategory.class));

        return editButton;
    }

    public void getCategoryData(List<CategoryData> theCategoryData){
       if (!theCategoryData.isEmpty()) {
            int lastIndex = theCategoryData.size() - 1;
            lastCategory = theCategoryData.get(lastIndex);
        }
    }

    public CategoryData getLastCategory() {
        return lastCategory;
    }

    //    public void setGoToEditClassStatus(boolean goToEditClassStatus) {
//        this.goToEditClassStatus = goToEditClassStatus;
//    }
//
//    public boolean isGoToEditClassStatus() {
//        return goToEditClassStatus;
//    }
}
