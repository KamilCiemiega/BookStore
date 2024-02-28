package app.views.UserPanel.Books.BookCreator;

import app.service.BookService;
import app.views.UserPanel.UserPanel;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
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
    private TextField assortmentField;

    private final ValidateAndSaveBook validateAndSaveBook;

    public BookCreator(BookService bookService) {
        validateAndSaveBook = new ValidateAndSaveBook(bookService);
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
        Button backButton = new Button("Back");
        backButton.addClassName("backButton");
        backButton.addClickListener(e -> UI.getCurrent().navigate("BookMainPanel"));

        buttonContainer.add(backButton, savaAndClose());

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
            validateAndSaveBook.validateBookData(
                    codeField.getValue(),
                    nameField.getValue(),
                    assortmentField.getValue(),
                    priceField.getValue()
            );
            displayErrorMessage(validateAndSaveBook.getErrors());
            if (validateAndSaveBook.getDataBaseStatus()){
                Notification notification = new Notification("Save successfully", 3000,Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.open();

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
    private void clearErrorMessages() {
        validateAndSaveBook.clearErrors();
    }
    protected FormLayout bookParameters() {
        FormLayout bookFormLayout = new FormLayout();

        codeField = new TextField();
        codeField.setRequired(true);
        bookFormLayout.addFormItem(codeField, "Code");

        bookFormLayout.addFormItem(createHiddenTextField(), "");

        nameField = new TextField();
        nameField.setRequired(true);
        bookFormLayout.addFormItem(nameField, "Name");

        bookFormLayout.addFormItem(createHiddenTextField(), "");

        assortmentField = new TextField();
        bookFormLayout.addFormItem(assortmentField, "Assortment");

        return bookFormLayout;
    }

    private FormLayout bookPrice() {
        FormLayout priceFormLayout = new FormLayout();
        priceFormLayout.addClassName("priceFormLayout");

        priceField = new TextField();
        priceField.setValue("0");
        priceField.addClassName("priceTextField");
        priceFormLayout.addFormItem(priceField, "Price");

        return priceFormLayout;
    }

    TextField createHiddenTextField(){
        TextField hiddenTextField = new TextField();
        hiddenTextField.setVisible(false);
        return hiddenTextField;
    }
}
