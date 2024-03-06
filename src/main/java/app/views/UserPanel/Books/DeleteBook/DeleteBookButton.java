package app.views.UserPanel.Books.DeleteBook;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.AddBook.BookCreator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;

public class DeleteBookButton {

    private final DeleteBook deleteBook;

    public DeleteBookButton(BookService bookService) {
        this.deleteBook = new DeleteBook(bookService);
    }

   public Button deleteButton(){
       Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
       deleteButton.addClassName("deleteButton");

       deleteButton.addClickListener(e -> {
           deleteBook.deleteBook();

       });
       return deleteButton;

    }
}
