package app.views.UserPanel.Category.AddCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.CategoryData;

import java.util.List;

public class AddCategoryServiceAction {

    private final CategoryService categoryService;
    private boolean sendCategoryStatus = false;
    public AddCategoryServiceAction(CategoryService categoryService) {
      this.categoryService = categoryService;
    }

    public void sendCategory(String categoryName, Integer parentId ){
        System.out.println("sendCategory" + parentId);
        boolean sendStatus = categoryService.addCategory(categoryName, parentId);
        if(sendStatus){
            sendCategoryStatus = true;
        }
    }

    public boolean isSendCategoryStatus() {
        return sendCategoryStatus;
    }

    public boolean isNameExist(String categoryName){
        List<CategoryData> allCategory = categoryService.getAllCategory();
        return allCategory.stream()
                .anyMatch(category -> category.name().equalsIgnoreCase(categoryName));
    }
}
