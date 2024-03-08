package app.views.UserPanel.Books.DeleteBook;

import app.service.BookService;
import app.views.UserPanel.Books.BookMainPanel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class DeleteBookButton {

    private final DeleteBook deleteBook;
    private final BookService bookService;

    public DeleteBookButton(BookService bookService) {
        this.bookService= bookService;
        this.deleteBook = new DeleteBook(bookService);
    }

    public Button deleteButton(){
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addClassName("deleteButton");

        deleteButton.addClickListener(e -> {
            boolean deleteBookStatus = deleteBook.deleteBookFromDatabase();
            if (deleteBookStatus){
                showNotification("Book deleted successfully", NotificationVariant.LUMO_SUCCESS);
            }else {
                showNotification("Can't delete book", NotificationVariant.LUMO_ERROR);
            }
        });
        return deleteButton;

    }

    private void showNotification(String message, NotificationVariant variant) {
        Notification notification = new Notification(
                message,
                3000,
                Notification.Position.TOP_CENTER
        );
        notification.addThemeVariants(variant);
        notification.open();
    }
}
