package app.views.UserPanel.Category.EditCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategory;
import app.views.UserPanel.Category.CategoryData;
import app.views.UserPanel.Category.CategoryTreeView;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "EditCategory", layout = UserPanel.class)
public class EditCategory extends AddCategory {

    private CategoryData lastCategory;

    private final CategoryService categoryService;
    @Autowired
    public EditCategory(CategoryService categoryService) {
        super(categoryService);
        this.categoryService = categoryService;
    }
    @Override
    protected VerticalLayout formContainer() {
        VerticalLayout formLayout = super.formContainer();

        if(getCategoryData() != null) {
            if (getCategoryData().parentId() == null) {
                mainCategory.setValue("test");
                categoryName.setValue(getCategoryData().name());
            } else {
                categoryName.setValue(getCategoryData().name());
//                List<CategoryData> allCategories = ;
//                System.out.println(categoryService.getAllCategory());
//                if (allCategories != null) {
//                    Optional<String> categoryName = allCategories.stream()
//                            .filter(category -> Objects.equals(category.categoryId(), getCategoryData().parentId()))
//                            .map(CategoryData::name)
//                            .findFirst();
//
//                    categoryName.ifPresent(mainCategory::setValue);
//
//                }
            }

        }
        return formLayout;
    }

    public CategoryData getCategoryData() {
        List<CategoryData> listOfCategoryData = CategoryTreeView.getListOfClickedCategorise();
        if (!listOfCategoryData.isEmpty()) {
            int lastIndex = listOfCategoryData.size() - 1;
            lastCategory = listOfCategoryData.get(lastIndex);

            System.out.println(lastCategory);

            return lastCategory;
        }
        return null;
    }


}
