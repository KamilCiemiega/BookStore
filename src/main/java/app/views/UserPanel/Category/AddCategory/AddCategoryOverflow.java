package app.views.UserPanel.Category.AddCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.CategoryOverflow;
import com.vaadin.flow.component.textfield.TextField;

public class AddCategoryOverflow extends CategoryOverflow {

    public AddCategoryOverflow(CategoryService categoryService, TextField mainCategory) {
        super();
        AddCategoryTreeView addCategoryTreeView = new AddCategoryTreeView(categoryService, mainCategory, this);

        add(addCategoryTreeView.displayTreeView());
    }
}
