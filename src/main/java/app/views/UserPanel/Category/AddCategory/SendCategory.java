package app.views.UserPanel.Category.AddCategory;

import app.service.CategoryService;

public class SendCategory {

    private final CategoryService categoryService;
    private boolean sendCategoryStatus = false;
    public SendCategory(CategoryService categoryService) {
      this.categoryService = categoryService;

    }

    public <T> void sendCategory(String categoryName, T parentId ){
        boolean sendStatus = categoryService.addCategory(categoryName, parentId);
        if(sendStatus){
            sendCategoryStatus = true;
        }
    }

    public boolean isSendCategoryStatus() {
        return sendCategoryStatus;
    }
}
