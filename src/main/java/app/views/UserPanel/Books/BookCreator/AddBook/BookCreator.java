package app.views.UserPanel.Books.BookCreator.AddBook;

import app.service.BookService;
import app.views.UserPanel.UserPanel;
import app.views.UserPanel.Utils.BackToMainButton;
import app.views.UserPanel.Utils.ShowNotification;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import java.util.Map;


@Route(value = "BookCreator", layout = UserPanel.class)
public class BookCreator extends VerticalLayout implements ViewConfigurator {
    protected TextField codeField;
    protected TextField nameField;
    protected TextField priceField;
    protected TextField assortmentField;

    private final AddBookToTheDatabase addBookToTheDatabase;

    public BookCreator(BookService bookService) {
        addBookToTheDatabase = new AddBookToTheDatabase(bookService);
        configureView();
        add(bookCreatorContainer());
    }

    @Override
    public void configureView() {
        setSizeFull();
        setClassName("BookCreator");
    }

    private VerticalLayout bookCreatorContainer() {
        VerticalLayout container = new VerticalLayout();

        HorizontalLayout buttonContainer = new HorizontalLayout();

        buttonContainer.add(BackToMainButton.backToMainButton(), savaAndClose());

        TabSheet tabSheet = new TabSheet();
        tabSheet.add("Book Parameters", bookParameters());
        tabSheet.add("Book Price", bookPrice());


        container.add(buttonContainer, tabSheet);
        return container;
    }
    protected Button savaAndClose() {
        Button saveAndClose = new Button("Save and Close");
        saveAndClose.addClassName("saveAndClose");

        saveAndClose.addClickListener(e -> {
            addBookToTheDatabase.validateBookData(
                    codeField.getValue(),
                    nameField.getValue(),
                    assortmentField.getValue(),
                    priceField.getValue()
            );
            displayErrorMessage(addBookToTheDatabase.getErrors());
            if (addBookToTheDatabase.getDatabaseStatus()){
                ShowNotification.showNotification("Save successfully",NotificationVariant.LUMO_SUCCESS);

                UI.getCurrent().navigate("BookMainPanel");
            }
        });
        return saveAndClose;
    }

    protected void displayErrorMessage(Map<String, String> errors) {

        for (String fieldName : errors.keySet()) {
            String errorMessage = errors.get(fieldName);
            switch (fieldName) {
                case "code":
                    codeField.setInvalid(true);
                    codeField.setErrorMessage(errorMessage);
                    break;
                case "name":
                    nameField.setInvalid(true);
                    nameField.setErrorMessage(errorMessage);
                    break;
                case "priceNumber":
                    priceField.setInvalid(true);
                    priceField.setErrorMessage(errorMessage);
                    break;
                case "pricePositiveNumber":
                    priceField.setInvalid(true);
                    priceField.setErrorMessage(errorMessage);
                    break;
                case "codeDuplicate":
                    codeField.setInvalid(true);
                    codeField.setErrorMessage(errorMessage);
                    break;
                case "nameDuplicate":
                    nameField.setInvalid(true);
                    nameField.setErrorMessage(errorMessage);
                    break;
                default:
                    break;
            }
        }
        clearErrorMessages();
    }
    protected void clearErrorMessages() {
        addBookToTheDatabase.clearErrors();
    }
    protected VerticalLayout bookParameters() {
//        FormLayout bookFormLayout = new FormLayout();
//
//        codeField = new TextField();
//        codeField.setRequired(true);
//        bookFormLayout.addFormItem(codeField, "Code");
//
//        bookFormLayout.addFormItem(hiddenTextField(), "");
//
//        nameField = new TextField();
//        nameField.setRequired(true);
//        bookFormLayout.addFormItem(nameField, "Name");
//
//        bookFormLayout.addFormItem(hiddenTextField(), "");
//        assortmentField = new TextField();
//        bookFormLayout.addFormItem(assortmentField, "Assortment");
//        return bookFormLayout;

        VerticalLayout bookParamContainer = new VerticalLayout();
        
        HorizontalLayout categoryFieldContainer = new HorizontalLayout();
        categoryFieldContainer.addClassName("categoryFieldContainer");
        categoryFieldContainer.setSpacing(false);

        Span categoryLabel = new Span("Category");
        categoryLabel.addClassName("categoryLabel");
        TextField categoryField = new TextField();
        Icon chooseCategoryIcon = new Icon(VaadinIcon.FOLDER);
        chooseCategoryIcon.setColor("white");
        Button chooseCategory = new Button(chooseCategoryIcon);
        chooseCategory.addClassName("chooseCategory");

        categoryFieldContainer.add(categoryLabel,categoryField, chooseCategory);
        
        bookParamContainer.add(categoryFieldContainer);

        return bookParamContainer;
    }

    private FormLayout bookPrice() {
        FormLayout priceFormLayout = new FormLayout();
        priceFormLayout.addClassName("priceFormLayout");

        priceField = new TextField();
        priceField.setValue("0.00");
        priceField.addClassName("priceTextField");
        priceFormLayout.addFormItem(priceField, "Price");

        return priceFormLayout;
    }
    private TextField hiddenTextField(){
        TextField hiddenTextField = new TextField();
        hiddenTextField.setVisible(false);
        return hiddenTextField;
    }
}
