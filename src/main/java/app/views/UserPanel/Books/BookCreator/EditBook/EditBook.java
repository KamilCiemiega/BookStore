package app.views.UserPanel.Books.BookCreator.EditBook;

import app.service.BookService;
import app.service.CategoryService;
import app.views.UserPanel.Books.BookCreator.AddBook.BookCreator;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook.GetSelectedBookValue;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook.SelectedBook;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;


@Route(value = "EditBook", layout = UserPanel.class)
public class EditBook extends BookCreator {
    
    private final UpdateBook updateBook;
    private final GetCategoryName getCategoryName;
    private final SelectedBook selectedBook = GetSelectedBookValue.selectedBook;

    public EditBook(CategoryService categoryService, BookService bookService) {
        super(categoryService, bookService);
        this.updateBook = new UpdateBook(bookService);
        this.getCategoryName = new GetCategoryName(bookService);
        setBookFields();
    }
    public void setBookFields() {
            if (selectedBook != null) {
                nameField.setValue(selectedBook.getBookName());
                priceField.setValue(selectedBook.getPrice().toString());
                codeField.setValue(selectedBook.getCode());
                String categoryName = getCategoryName.getCategoryNameById(selectedBook.getCategoryId());
                if(categoryName != null){
                    categoryField.setValue(categoryName);
                }
            }
    }

    @Override
    protected Button savaAndClose() {
        Button saveAndClose = new Button("Save and Close");
        saveAndClose.addClassName("saveAndClose");
        saveAndClose.addClickListener(e -> {
            updateBook.validateBookData(
                    codeField.getValue(),
                    nameField.getValue(),
                    categoryField.getValue(),
                    priceField.getValue(),
                    categoryId
            );
            displayErrorMessage(updateBook.getErrors());
            if (updateBook.isNoChanges()){
                UI.getCurrent().navigate("BookMainPanel");
            }
            if (updateBook.getDatabaseStatus()){
                Notification notification = new Notification("Save successfully", 3000,Notification.Position.TOP_CENTER);
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                notification.open();

                UI.getCurrent().navigate("BookMainPanel");
            }
        });

        return saveAndClose;
    }

    @Override
    protected void clearErrorMessages() {
        updateBook.clearErrors();
    }
}
