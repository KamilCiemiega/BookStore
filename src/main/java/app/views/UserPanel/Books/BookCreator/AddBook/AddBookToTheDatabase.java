package app.views.UserPanel.Books.BookCreator.AddBook;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.SendBookStatus;
import app.views.UserPanel.Books.BookCreator.ValidateBook;

import java.math.BigDecimal;

public class AddBookToTheDatabase extends ValidateBook implements SendBookStatus {

    protected final BookService bookService;
    private boolean dataBaseStatus = false;
    public AddBookToTheDatabase(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void validateBookData(String codeValue, String nameValue, String categoryValue, String priceValue, Integer categoryId) {
        super.validateBookData(codeValue, nameValue, categoryValue, priceValue, categoryId);
        if(bookService.bookExistsByCode(codeValue)){
            errors.put("codeDuplicate", "Book with that code already exist");
        }
        if(bookService.bookExistsByName(nameValue)){
            errors.put("nameDuplicate", "Book with that name already exist");
        }
        if(errors.isEmpty()){
            dataBaseStatus = true;
            sendBookDataToDatabase(codeValue, nameValue, priceBigDecimal, categoryId);
        }
    }

    public void sendBookDataToDatabase(String codeValue, String nameValue, BigDecimal priceValue, Integer categoryId) {
        bookService.insertBook(nameValue,codeValue, priceValue, categoryId);
    }

    @Override
    public boolean getDatabaseStatus() {
        return dataBaseStatus;
    }
}
