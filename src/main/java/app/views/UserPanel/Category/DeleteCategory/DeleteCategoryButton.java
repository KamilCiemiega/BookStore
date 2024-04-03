package app.views.UserPanel.Category.DeleteCategory;

import app.views.UserPanel.Category.CategoryData;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.List;

public class DeleteCategoryButton {

    private int categoryId;

    public Button deleteButton(){
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addClassName("editButton");
        deleteButton.addClickListener(e -> System.out.println("click"));

        return deleteButton;
    }

    public void getCategoryData(List<CategoryData> theCategoryData){
        if (!theCategoryData.isEmpty()) {
            CategoryData lastCategory = theCategoryData.get(theCategoryData.size() - 1);
            categoryId = lastCategory.categoryId();
        }
    }
}
