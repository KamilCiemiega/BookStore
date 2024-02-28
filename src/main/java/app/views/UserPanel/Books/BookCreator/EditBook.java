package app.views.UserPanel.Books.BookCreator;

import app.service.BookService;
import app.views.UserPanel.UserPanel;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;


@Route(value = "EditBook", layout = UserPanel.class)
public class EditBook extends BookCreator{
    SelectedBook selectedBook;
    public EditBook(BookService bookService) {
        super(bookService);
        setBookFields();
    }
    public void setBookFields() {
        selectedBook = (SelectedBook) UI.getCurrent().getSession().getAttribute("selectedBook");

            if (selectedBook != null && selectedBook.getBookName() != null) {
                nameField.setValue(selectedBook.getBookName());
                priceField.setValue(selectedBook.getPrice().toString());
                codeField.setValue(selectedBook.getCode());
            }
    }
}
