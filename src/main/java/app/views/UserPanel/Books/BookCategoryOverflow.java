package app.views.UserPanel.Books;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategoryTreeView;
import app.views.UserPanel.Category.CategoryOverflow;
import com.vaadin.flow.component.textfield.TextField;


public class BookCategoryOverflow extends CategoryOverflow {
    public BookCategoryOverflow(CategoryService categoryService, TextField categoryField) {
        super();
        AddCategoryTreeView addCategoryTreeView = new AddCategoryTreeView(categoryService, categoryField, this);
        add(addCategoryTreeView.displayTreeView());
    }
}
