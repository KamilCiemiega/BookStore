package app.views.UserPanel.Category.AddCategory;


import app.service.CategoryService;
import app.views.UserPanel.UserPanel;
import app.views.UserPanel.Utils.BackToMainButton;
import app.views.UserPanel.Utils.ShowNotification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route(value = "AddCategory", layout = UserPanel.class)
public class AddCategory extends VerticalLayout {

    private final TextField  categoryName = new TextField();
    private final SendCategory sendCategory;

    public AddCategory(CategoryService categoryService) {
        this.sendCategory = new SendCategory(categoryService);
        CategoryForm categoryForm = new CategoryForm();
        add(buttonContainer(), categoryForm.formContainer(categoryName));
    }

    private HorizontalLayout buttonContainer() {
        HorizontalLayout buttonContainer = new HorizontalLayout();
        buttonContainer.add(BackToMainButton.backToMainButton(), saveAndCloseButton());
        return buttonContainer;
    }



    private Button saveAndCloseButton() {
        Button saveAndClose = new Button("Save and Close");
        saveAndClose.addClassName("saveAndClose");
        saveAndClose.addClickListener(e -> {
            if(validateCategoryName(categoryName)) {
                String categoryNameValue = categoryName.getValue();
                sendCategory.sendCategory(categoryNameValue, null);
                if (sendCategory.isSendCategoryStatus()){
                    ShowNotification.showNotification("Save Successfully", NotificationVariant.LUMO_SUCCESS);
                    UI.getCurrent().navigate("BookMainPanel");
                }else {
                    ShowNotification.showNotification("Something get wrong please contact with support", NotificationVariant.LUMO_ERROR);
                }

            }

        });

        return saveAndClose;
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
