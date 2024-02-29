package app.views.UserPanel.Books.BookCreator.EditBook;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.AddBook.BookCreator;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.Route;


@Route(value = "EditBook", layout = UserPanel.class)
public class EditBook extends BookCreator {
    
    private final UpdateBook updateBook;
    public EditBook(BookService bookService) {
        super(bookService);
        this.updateBook = new UpdateBook(bookService);
        setBookFields();
    }
    public void setBookFields() {
       SelectedBook selectedBook = (SelectedBook) UI.getCurrent().getSession().getAttribute("selectedBook");

            if (selectedBook != null) {
                nameField.setValue(selectedBook.getBookName());
                priceField.setValue(selectedBook.getPrice().toString());
                codeField.setValue(selectedBook.getCode());
            }
    }
    @Override
    protected void clearErrorMessages() {
        updateBook.clearErrors();
    }

    @Override
    protected Button savaAndClose() {
        updateBook.getDataBaseStatus();
        return super.savaAndClose();
    }


    //    @Override
//    protected Button savaAndClose() {
////        Button saveAndClose = super.savaAndClose();
//
////        updateBook.validateBookData(
////                "123",
////                "asd",
////                "",
////                "123"
////        );
//
////        if (updateBook.getDataBaseStatus()){
////            Notification notification = new Notification("Save successfully", 3000, Notification.Position.TOP_CENTER);
////            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
////            notification.open();
////
////            UI.getCurrent().navigate("BookMainPanel");
////        }
//       return new Button();
//    }


}
