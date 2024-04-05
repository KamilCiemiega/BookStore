package app.views.UserPanel.Category.EditCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.AddCategory.AddCategory;
import app.views.UserPanel.Category.CategoryData;
import app.views.UserPanel.Category.CategoryTreeView;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Optional;

@Route(value = "EditCategory", layout = UserPanel.class)
public class EditCategory extends AddCategory {

    private CategoryData lastCategory;

    private final CategoryService categoryService;

    private String initialMainCategoryValue;

    public EditCategory(CategoryService categoryService) {
        super(categoryService);
        this.categoryService = categoryService;
    }

    @Override
    protected VerticalLayout formContainer() {
        VerticalLayout formLayout = super.formContainer();
        if (getClickedCategoryData() != null) {
            if (getClickedCategoryData().parentId() == 0) {
                mainCategory.setValue("It's main category");
                categoryName.setValue(getClickedCategoryData().name());
            } else {
                categoryName.setValue(getClickedCategoryData().name());

                Optional<String> categoryName = allCategoryFromService.stream()
                        .filter(category -> category.categoryId() == getClickedCategoryData().parentId())
                        .map(CategoryData::name)
                        .findFirst();

                categoryName.ifPresent(mainCategory::setValue);
                initialMainCategoryValue = mainCategory.getValue();
            }
        }
        return formLayout;
    }

    private CategoryData getClickedCategoryData() {
        List<CategoryData> listOfCategoryData = CategoryTreeView.getListOfClickedCategorise();
        if (!listOfCategoryData.isEmpty()) {
            int lastIndex = listOfCategoryData.size() - 1;
            lastCategory = listOfCategoryData.get(lastIndex);


            return lastCategory;
        }
        return null;
    }

    @Override
    protected boolean validateCategoryName(TextField categoryField) {
        String categoryFieldValue = categoryField.getValue();

        if (categoryFieldValue.isEmpty()) {
            categoryField.setInvalid(true);
            categoryField.setErrorMessage("Cannot be empty");
            return false;
        }else if (allCategoryFromService != null){

            if (addCategoryServiceAction.isNameExist(categoryFieldValue)){
                System.out.println(initialMainCategoryValue + mainCategory.getValue());
                if(initialMainCategoryValue.equals(mainCategory.getValue())){
                    categoryField.setInvalid(true);
                    categoryField.setErrorMessage("Category with that name already exist");
                    return false;
                }
               System.out.println("test");
            }

        }
        return true;
    }
}
