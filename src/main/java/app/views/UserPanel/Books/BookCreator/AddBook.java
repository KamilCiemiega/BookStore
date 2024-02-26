package app.views.UserPanel.Books.BookCreator;
import app.service.BookService;
import java.math.BigDecimal;

public class AddBook extends  ValidateAndSaveBook{
    public AddBook(BookService bookService) {
        super(bookService);
    }

    @Override
    protected void sendDataToDatabase(String codeValue, String nameValue, BigDecimal priceValue) {
        bookService.insertBook(nameValue,Integer.parseInt(codeValue), priceValue);
    }
}
