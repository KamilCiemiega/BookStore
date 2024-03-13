package app.views.UserPanel.Books.DeleteBook;

import app.service.BookService;
import app.views.UserPanel.Books.BookMainPanel;
import app.views.UserPanel.Utils.ShowNotification;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class DeleteBookButton {

    private final DeleteBook deleteBook;

    public DeleteBookButton(BookService bookService, BookGridRefresher gridRefresher) {
        this.deleteBook = new DeleteBook(bookService, gridRefresher);
    }

    public Button deleteButton(){
        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addClassName("deleteButton");

        deleteButton.addClickListener(e -> {
            boolean deleteBookStatus = deleteBook.deleteBookFromDatabase();
            if (deleteBookStatus){
                ShowNotification.showNotification("Book deleted successfully",NotificationVariant.LUMO_SUCCESS );
            }else {
                ShowNotification.showNotification("Can't delete book",NotificationVariant.LUMO_ERROR );
            }
        });
        return deleteButton;

    }

}
