package app.views.UserPanel.Books.BookCreator;

import app.service.BookService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public abstract class AbstractBook extends VerticalLayout {
    protected TextField codeField;
    protected TextField nameField;
    protected TextField assortmentField;
    protected TextField priceField;
    protected ComboBox<String> discountComboBox;
    protected final BookService bookService;
    protected final ValidateAndAddBook validateAndAddBook;

    public AbstractBook(BookService bookService) {
        this.bookService = bookService;
        validateAndAddBook = new ValidateAndAddBook(bookService);
    }
}
