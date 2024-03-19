package app.views.UserPanel.Category.AddCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.CategoryData;
import app.views.UserPanel.Category.CategoryOverflow;
import app.views.UserPanel.Category.CategoryTreeView;
import com.vaadin.flow.component.textfield.TextField;

public class AddCategoryTreeView extends CategoryTreeView {
    private final TextField mainCategory;
    private CategoryData lastClickedCategory;
    private Integer categoryId;
    private String categoryName;
    private final CategoryOverflow categoryOverflow;


    public AddCategoryTreeView(CategoryService categoryService, TextField mainCategory, CategoryOverflow categoryOverflow) {
        super(categoryService);
        this.mainCategory = mainCategory;
        this.categoryOverflow = categoryOverflow;
    }

    @Override
    protected void handleCategoryClick(CategoryData category) {

        lastClickedCategory = category;
        categoryId = category.categoryId();
        categoryName = category.name();
        setLastClickedCategoryValues();
        categoryOverflow.updateChooseInformation(categoryName);

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

    public Integer getCategoryId() {
        return categoryId;
    }
}
