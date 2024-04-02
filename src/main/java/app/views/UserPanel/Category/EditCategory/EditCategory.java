package app.views.UserPanel.Category.EditCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategory;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "EditCategory", layout = UserPanel.class)
public class EditCategory extends AddCategory {


    private final EditCategoryButton editCategoryButton;

    public EditCategory(CategoryService categoryService) {
        super(categoryService);
        this.editCategoryButton = new EditCategoryButton();
    }

    @Override
    protected VerticalLayout formContainer() {
        VerticalLayout formLayout = super.formContainer();

        if(editCategoryButton.getLastCategory() != null){
            System.out.println(editCategoryButton.getLastCategory());
        }

//        if (editCategoryButton.getLastCategory().parentId() == null){
//            mainCategory.setValue("It's main category");
//        }

        return formLayout;
    }


}
