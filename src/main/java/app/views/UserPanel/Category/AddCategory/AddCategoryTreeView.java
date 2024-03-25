package app.views.UserPanel.Category.AddCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.CategoryData;
import app.views.UserPanel.Category.CategoryTreeView;
import com.vaadin.flow.component.textfield.TextField;

public class AddCategoryTreeView extends CategoryTreeView {
    private final TextField mainCategory;
    private CategoryData lastClickedCategory;
    private Integer categoryId;
    private String categoryName;
    private final AddCategoryOverflow addCategoryOverflow;


    public AddCategoryTreeView(CategoryService categoryService, TextField mainCategory, AddCategoryOverflow addCategoryOverflow) {
        super(categoryService);
        this.mainCategory = mainCategory;
        this.addCategoryOverflow = addCategoryOverflow;
    }

    @Override
    public void handleCategoryClick(CategoryData category) {
        lastClickedCategory = category;
        categoryId = category.categoryId();
        categoryName = category.name();
        setLastClickedCategoryValues();
        AddCategory.addCategoryParentId(categoryId);
        addCategoryOverflow.updateChooseInformation(categoryName);
    }

    public void setLastClickedCategoryValues() {
        if (lastClickedCategory != null) {
            categoryId = lastClickedCategory.categoryId();
            categoryName = lastClickedCategory.name();
            mainCategory.setValue(categoryName);
        } else {
            mainCategory.setValue("It's gonna be main category");
        }
    }
}
