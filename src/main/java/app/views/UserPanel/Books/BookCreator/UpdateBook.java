package app.views.UserPanel.Books.BookCreator;

import app.service.BookService;

import java.math.BigDecimal;

public class UpdateBook extends  ValidateAndSaveBook{


    public UpdateBook(BookService bookService) {
        super(bookService);
    }

    @Override
    protected void sendDataToDatabase(String codeValue, String nameValue, BigDecimal priceValue) {

    }
}
