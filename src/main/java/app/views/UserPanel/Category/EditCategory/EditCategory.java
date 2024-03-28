package app.views.UserPanel.Category.EditCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategory;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.router.Route;

@Route(value = "EditCategory", layout = UserPanel.class)
public class EditCategory extends AddCategory {

    public EditCategory(CategoryService categoryService) {
        super(categoryService);
    }
}
