package app.views.UserPanel.Category.EditCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategory;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "EditCategory", layout = UserPanel.class)
public class EditCategory extends AddCategory {

    private final EditCategoryButton editCategoryButton;

    @Autowired
    public EditCategory(CategoryService categoryService, EditCategoryButton editCategoryButton) {
        super(categoryService);
        this.editCategoryButton = editCategoryButton;
        test();
    }

    @Override
    protected VerticalLayout formContainer() {
        VerticalLayout formLayout = super.formContainer();


//        if (editCategoryButton.getLastCategory().parentId() == null){
//            mainCategory.setValue("It's main category");
//        }

        return formLayout;
    }

    private void test(){
        editCategoryButton.editButton();
    }
}
