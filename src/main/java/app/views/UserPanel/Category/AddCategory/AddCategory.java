package app.views.UserPanel.Category.AddCategory;


import app.views.UserPanel.UserPanel;
import app.views.UserPanel.Utils.BackToMainButton;
import app.views.UserPanel.Utils.HiddenTextField;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("AddCategory")
public class AddCategory extends VerticalLayout {

    private TextField categoryName = new TextField();
    public AddCategory() {
        add(buttonContainer(), bookParameters());
    }

    private HorizontalLayout buttonContainer() {
        HorizontalLayout buttonContainer = new HorizontalLayout();
        buttonContainer.add(BackToMainButton.backToMainButton(), saveAndCloseButton());
        return buttonContainer;
    }

    private FormLayout bookParameters() {
        FormLayout bookFormLayout = new FormLayout();
        TextField mainCategory = new TextField();
        mainCategory.setReadOnly(true);
        mainCategory.setValue("It's gonna be main category");
        bookFormLayout.addFormItem(mainCategory, "Main Category");
        bookFormLayout.addFormItem(HiddenTextField.createHiddenTextField(), "");

        bookFormLayout.addFormItem(categoryName, "Category Name");
        bookFormLayout.addFormItem(HiddenTextField.createHiddenTextField(), "");
        return bookFormLayout;
    }

    private Button saveAndCloseButton() {
        Button saveAndClose = new Button("Save and Close");
        saveAndClose.addClassName("saveAndClose");
        saveAndClose.addClickListener(e -> {
            if(validateCategoryName(categoryName)) {
                UI.getCurrent().navigate("BookMainPanel");
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
