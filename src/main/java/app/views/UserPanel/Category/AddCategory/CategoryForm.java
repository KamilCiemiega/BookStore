package app.views.UserPanel.Category.AddCategory;

import app.views.UserPanel.Category.CategoryOverflow;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class CategoryForm extends VerticalLayout{

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
            CategoryOverflow categoryOverflow = new CategoryOverflow();
            categoryOverflow.open();
        });

        Icon cleanCategoryIcon = new Icon(VaadinIcon.CLOSE);
        cleanCategoryIcon.setColor("white");
        Button cleanCategory = new Button(cleanCategoryIcon);
        cleanCategory.addClassName("cleanCategory");
        TextField mainCategory = new TextField();
        mainCategory.addClassName("mainCategory");
        mainCategory.setReadOnly(true);
        mainCategory.setValue("It's gonna be main category");

        Span secondFormLabelName = new Span("Category Name");
        categoryName.addClassName("categoryName");
        HorizontalLayout categoryNameContainer = new HorizontalLayout();
        categoryNameContainer.addClassName("categoryNameContainer");
        categoryNameContainer.add(secondFormLabelName, categoryName);

        mainCategoryContainer.add(firstFormLabelName, mainCategory, chooseCategory,cleanCategory);

        categoryFormLayout.add(mainCategoryContainer, categoryNameContainer);

        return categoryFormLayout;
    }
}
