package app.views.UserPanel.Books.BookCreator;

import app.service.BookService;
import app.views.UserPanel.UserPanel;
import app.views.ViewConfigurator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.math.BigDecimal;
import java.util.Map;


@Route(value = "BookCreator", layout = UserPanel.class)
public class BookCreator extends VerticalLayout implements ViewConfigurator {
    private TextField codeField;
    private TextField nameField;
    private TextField assortmentField;
    private TextField priceField;
    private ComboBox<String> discountComboBox;
    private final BookService bookService;
    private final ValidateAndAddBook validateAndAddBook;
    public BookCreator(BookService bookService) {
        this.bookService = bookService;
        validateAndAddBook = new ValidateAndAddBook(bookService);
        configureView();
        add(bookCreatorContainer());

//        codeField.addValueChangeListener(event -> clearErrorMessages());
//        nameField.addValueChangeListener(event -> clearErrorMessages());
//        priceField.addValueChangeListener(event -> validateAndAddBook.clearErrors("priceNumber"));
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
    private Button savaAndClose() {
        Button saveAndClose = new Button("Save and Close");
        saveAndClose.addClassName("saveAndClose");

        saveAndClose.addClickListener(e -> {
            validateAndAddBook.validateBookData(
                    codeField.getValue(),
                    nameField.getValue(),
                    assortmentField.getValue(),
                    priceField.getValue(),
                    discountComboBox.getValue()
            );
            displayErrorMessage(validateAndAddBook.getErrors());
            if (validateAndAddBook.getDataBaseStatus()){
                System.out.println(validateAndAddBook.getDataBaseStatus());
                Notification notification = new Notification("Save successfully", 3000,Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.open();

                UI.getCurrent().navigate("BookMainPanel");
            }

        });
        return saveAndClose;
    }

    private void displayErrorMessage(Map<String, String> errors) {

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
        validateAndAddBook.clearErrors();
    }
    private FormLayout bookParameters() {
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
        priceField.addClassName("priceTextField");
        priceFormLayout.addFormItem(priceField, "Price");

        priceFormLayout.addFormItem(createHiddenTextField(), "");

        discountComboBox = new ComboBox<>();
        discountComboBox.setItems("amount discount", "percentage discount");
        priceFormLayout.addFormItem(discountComboBox, "Choose discount");

        return priceFormLayout;
    }

    TextField createHiddenTextField(){
        TextField hiddenTextField = new TextField();
        hiddenTextField.setVisible(false);
        return hiddenTextField;
    }
}
