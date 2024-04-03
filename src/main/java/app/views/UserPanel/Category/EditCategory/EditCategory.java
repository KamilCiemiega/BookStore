package app.views.UserPanel.Category.EditCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategory;
import app.views.UserPanel.Category.CategoryData;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route(value = "EditCategory", layout = UserPanel.class)
public class EditCategory extends AddCategory {
    private List<CategoryData> listOfCategoryData;
    private CategoryData lastCategory;

    public EditCategory(CategoryService categoryService) {
        super(categoryService);
    }
    @Override
    protected VerticalLayout formContainer() {
        VerticalLayout formLayout = super.formContainer();


//        System.out.println("formContainer" + listOfCategoryData);
//        mainCategory.setValue(listOfCategoryData.name);
//        System.out.println("wywołanie");
//        getCategoryData();
//        System.out.println("formcontainer" + lastCategory);

        return formLayout;
    }

    public void getCategoryData(List<CategoryData> categoryData) {
//        System.out.println("get" + categoryData);
//        if (!listOfCategoryData.isEmpty()) {
//            int lastIndex = listOfCategoryData.size() - 1;
//            lastCategory = listOfCategoryData.get(lastIndex);
//        }
//        System.out.println(lastCategory);
    }


}
