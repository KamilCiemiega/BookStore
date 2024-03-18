package app.views.UserPanel.Category.AddCategory;

import app.service.CategoryService;
import app.views.UserPanel.Category.CategoryOverflow;
import app.views.UserPanel.Category.CategoryTreeView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CategoryForm extends VerticalLayout {

    private final CategoryService categoryService;
    private final CategoryTreeView categoryTreeView;

    private final TextField mainCategory = new TextField();
    public CategoryForm(CategoryService categoryService ) {
        this.categoryService = categoryService;
        this.categoryTreeView = new CategoryTreeView(categoryService);
    }

    public VerticalLayout formContainer(TextField categoryName) {
        VerticalLayout categoryFormLayout = new VerticalLayout();

        HorizontalLayout mainCategoryContainer = new HorizontalLayout();
        mainCategoryContainer.addClassName("mainCategoryContainer");
        mainCategoryContainer.setSpacing(false);

        Span firstFormLabelName = new Span("Parent Category");
        firstFormLabelName.addClassName("firstFormLabelName");
        Icon chooseCategoryIcon = new Icon(VaadinIcon.FOLDER);
        chooseCategoryIcon.setColor("white");
        Button chooseCategory = new Button(chooseCategoryIcon);
        chooseCategory.addClassName("chooseCategory");
        chooseCategory.addClickListener(e -> {
            CategoryOverflow categoryOverflow = new CategoryOverflow(categoryService);
            categoryOverflow.open();
        });

        Icon cleanCategoryIcon = new Icon(VaadinIcon.CLOSE);
        cleanCategoryIcon.setColor("white");
        Button cleanCategory = new Button(cleanCategoryIcon);
        cleanCategory.addClassName("cleanCategory");
        mainCategory.setReadOnly(true);
        mainCategory.addClassName("mainCategory");



        Span secondFormLabelName = new Span("Category Name");
        categoryName.addClassName("categoryName");
        HorizontalLayout categoryNameContainer = new HorizontalLayout();
        categoryNameContainer.addClassName("categoryNameContainer");
        categoryNameContainer.add(secondFormLabelName, categoryName);

        mainCategoryContainer.add(firstFormLabelName, mainCategory, chooseCategory,cleanCategory);
        categoryTreeView.setLastClickedCategoryValues();

        categoryFormLayout.add(mainCategoryContainer, categoryNameContainer);

        return categoryFormLayout;
    }
}
