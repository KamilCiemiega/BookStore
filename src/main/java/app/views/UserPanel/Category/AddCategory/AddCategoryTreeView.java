package app.views.UserPanel.Category.AddCategory;

import app.service.CategoryService;
import app.views.UserPanel.Books.BookCreator.AddBook.BookCreator;
import app.views.UserPanel.Category.AbstractCategoryTreeView;
import app.views.UserPanel.Category.CategoryData;
import app.views.UserPanel.Category.CategoryOverflow;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AddCategoryTreeView<T extends CategoryOverflow> extends AbstractCategoryTreeView {
    private final TextField mainCategory;
    private CategoryData lastClickedCategory;
    private Integer categoryId;
    private String categoryName;
    private final T categoryOverflow;
    private final CategoryService categoryService;

    public AddCategoryTreeView(CategoryService categoryService, TextField mainCategory, T categoryOverflow) {
        super(categoryService);
        this.categoryService = categoryService;
        this.mainCategory = mainCategory;
        this.categoryOverflow = categoryOverflow;
    }

    @Override
    public void handleCategoryClick(CategoryData category, HorizontalLayout layout) {
        lastClickedCategory = category;
        categoryId = category.categoryId();
        categoryName = category.name();
        setLastClickedCategoryValues();
        AddCategory.getCategoryData(category);
        BookCreator.setCategoryId(categoryId);
        categoryOverflow.updateChooseInformation(categoryName);
    }

    public void setLastClickedCategoryValues() {
        if (lastClickedCategory != null) {
            categoryId = lastClickedCategory.categoryId();
            categoryName = lastClickedCategory.name();
            mainCategory.setValue(categoryName);
        } else if(categoryOverflow instanceof AddCategoryOverflow){
            mainCategory.setValue("It's gonna be main category");
        }else {
            mainCategory.setValue("Choose category from list");
        }
    }
}
