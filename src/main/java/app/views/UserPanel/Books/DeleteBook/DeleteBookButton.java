package app.views.UserPanel.Books.DeleteBook;

import app.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

public class DeleteBookButton {

    private final DeleteBook deleteBook;

    public DeleteBookButton(BookService bookService) {
        this.deleteBook = new DeleteBook(bookService);
    }

   public Button deleteButton(){
       Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
       deleteButton.addClassName("deleteButton");

       deleteButton.addClickListener(e -> {
            boolean deleteBookStatus = deleteBook.deleteBook();
           if (deleteBookStatus){
               Notification notification = new Notification(
                       "Książka została pomyślnie usunięta.",
                       3000,
                       Notification.Position.TOP_CENTER
               );
               notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
               notification.open();
           }
       });
       return deleteButton;

    }
}
