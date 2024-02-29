package app.views.UserPanel.Books.BookCreator.AddBook;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.ValidateBook;

import java.math.BigDecimal;

public class SendBookToTheDatabase extends ValidateBook {

    protected final BookService bookService;
    protected boolean dataBaseStatus = false;
    public SendBookToTheDatabase(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void validateBookData(String codeValue, String nameValue, String assortmentValue, String priceValue) {
        super.validateBookData(codeValue, nameValue, assortmentValue, priceValue);
        if(bookService.bookExistsByCode(codeValue)){
            errors.put("codeDuplicate", "Book with that code already exist");
        }
        if(bookService.bookExistsByName(nameValue)){
            errors.put("nameDuplicate", "Book with that name already exist");
        }
        if(errors.isEmpty()){
            dataBaseStatus = true;
            sendDataToDatabase(codeValue, nameValue, priceBigDecimal);
        }
    }

    protected void sendDataToDatabase(String codeValue, String nameValue, BigDecimal priceValue){
        bookService.insertBook(nameValue,codeValue, priceValue);
    }
    public boolean getDataBaseStatus() {
        return dataBaseStatus;
    }


}
