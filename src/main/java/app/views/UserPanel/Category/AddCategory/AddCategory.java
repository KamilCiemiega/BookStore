package app.views.UserPanel.Category.AddCategory;


import app.service.CategoryService;
import app.views.UserPanel.Category.CategoryOverflow;
import app.views.UserPanel.UserPanel;
import app.views.UserPanel.Utils.BackToMainButton;
import app.views.UserPanel.Utils.ShowNotification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route(value = "AddCategory", layout = UserPanel.class)
public class AddCategory extends VerticalLayout {

    private final TextField  categoryName = new TextField();
    private final SendCategory sendCategory;
    private final TextField mainCategory;
    private final CategoryOverflow categoryOverflow;

    private final AddCategoryTreeView addCategoryTreeView;

    public AddCategory(CategoryService categoryService) {
        this.mainCategory = new TextField();
        this.sendCategory = new SendCategory(categoryService);
        this.categoryOverflow = new CategoryOverflow(categoryService, mainCategory);
        addCategoryTreeView = new AddCategoryTreeView(categoryService, mainCategory, categoryOverflow);
        add(buttonContainer(), formContainer());
    }

    private HorizontalLayout buttonContainer() {
        HorizontalLayout buttonContainer = new HorizontalLayout();
        buttonContainer.add(BackToMainButton.backToMainButton(), saveAndCloseButton());
        return buttonContainer;
    }

    public VerticalLayout formContainer() {
        VerticalLayout categoryFormLayout = new VerticalLayout();

        HorizontalLayout mainCategoryContainer = new HorizontalLayout();
        mainCategoryContainer.addClassName("mainCategoryContainer");
        mainCategoryContainer.setSpacing(false);

        Span firstFormLabelName = new Span("Parent Category");
        firstFormLabelName.addClassName("firstFormLabelName");
        mainCategory.setValue("It's gonna be main category");
        mainCategory.addClassName("mainCategory");
        mainCategory.setReadOnly(true);
        Icon chooseCategoryIcon = new Icon(VaadinIcon.FOLDER);
        chooseCategoryIcon.setColor("white");
        Button chooseCategory = new Button(chooseCategoryIcon);
        chooseCategory.addClassName("chooseCategory");
        chooseCategory.addClickListener(e -> {
            categoryOverflow.open();
        });

        Icon cleanCategoryIcon = new Icon(VaadinIcon.CLOSE);
        cleanCategoryIcon.setColor("white");
        Button cleanCategory = new Button(cleanCategoryIcon);
        cleanCategory.addClassName("cleanCategory");
        cleanCategory.addClickListener(e -> mainCategory.setValue("It's gonna be main category"));

        Span secondFormLabelName = new Span("Category Name");
        categoryName.addClassName("categoryName");
        HorizontalLayout categoryNameContainer = new HorizontalLayout();
        categoryNameContainer.addClassName("categoryNameContainer");
        categoryNameContainer.add(secondFormLabelName, categoryName);

        mainCategoryContainer.add(firstFormLabelName,mainCategory,chooseCategory,cleanCategory);

        categoryFormLayout.add(mainCategoryContainer, categoryNameContainer);

        return categoryFormLayout;
    }
    private Button saveAndCloseButton() {
        Button saveAndClose = new Button("Save and Close");
        saveAndClose.addClassName("saveAndClose");
        saveAndClose.addClickListener(e -> {
            if(validateCategoryName(categoryName)) {
                String categoryNameValue = categoryName.getValue();
                if (mainCategory.getValue().equals("It's gonna be main category")){

                sendCategory.sendCategory(categoryNameValue, null);
                sentStatusNotification(sendCategory);
            }else {
                    sendCategory.sendCategory(categoryNameValue, addCategoryTreeView.getCategoryId());
                }
            }

        });

        return saveAndClose;
    }

    private void sentStatusNotification(SendCategory sendCategory){
        if (sendCategory.isSendCategoryStatus()) {
            ShowNotification.showNotification("Save Successfully", NotificationVariant.LUMO_SUCCESS);
            UI.getCurrent().navigate("BookMainPanel");
        } else {
            ShowNotification.showNotification("Something get wrong please contact with support", NotificationVariant.LUMO_ERROR);
        }
    }

    private boolean validateCategoryName(TextField categoryField) {
        boolean isEmpty = categoryField.getValue().isEmpty();
        if (isEmpty) {
            categoryField.setInvalid(true);
            categoryField.setErrorMessage("Cannot be empty");
            return false;
        }
        return true;
    }
}
