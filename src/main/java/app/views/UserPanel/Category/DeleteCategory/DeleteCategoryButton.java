package app.views.UserPanel.Category.DeleteCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.CategoryData;
import app.views.UserPanel.Category.CategoryTreeView;
import app.views.UserPanel.Utils.ShowNotification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;

import java.util.ArrayList;
import java.util.List;

public class DeleteCategoryButton implements CategoryDeletedListener {

    private int categoryId;
    private final List<CategoryData> clickedCategoryData = CategoryTreeView.getListOfClickedCategorise();
    private final CategoryService categoryService;
    private final DeleteCategoryOverflow deleteCategoryOverflow;
    private final List<CategoryDeletedListener> listeners = new ArrayList<>();

    public DeleteCategoryButton(CategoryService categoryService) {
        this.categoryService = categoryService;
        this.deleteCategoryOverflow = new DeleteCategoryOverflow();
    }

    public Button deleteButton(){
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addClassName("editButton");
        deleteButton.addClickListener(e -> {
            if (!clickedCategoryData.isEmpty()) {
                CategoryData lastCategory = clickedCategoryData.get(clickedCategoryData.size() - 1);
                categoryId = lastCategory.categoryId();

                if(categoryService.checkIfBookWithCategoryExist(categoryId)){
                    deleteCategoryOverflow.open();
                }else {
                  if (categoryService.deleteCategory(categoryId)){
                      ShowNotification.showNotification("Category deleted successfully", NotificationVariant.LUMO_SUCCESS);
                      for (CategoryDeletedListener listener : listeners) {
                          listener.categoryDeleted(lastCategory);
                      }
                  }else {
                      ShowNotification.showNotification("Something get wrong please contact with support", NotificationVariant.LUMO_ERROR);
                  }
                }
            }
        });

        return deleteButton;
    }

    public void addCategoryDeletedListener(CategoryDeletedListener listener) {
        listeners.add(listener);
    }
    @Override
    public void categoryDeleted(CategoryData deletedCategory) {

    }
}
