package app.views.UserPanel.Books.BookCreator.EditBook;


import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.SendBookStatus;
import app.views.UserPanel.Books.BookCreator.ValidateBook;
import app.views.UserPanel.Books.BookMainPanel;


import java.math.BigDecimal;

public class UpdateBook extends ValidateBook implements SendBookStatus {

    protected final BookService bookService;
    private boolean dataBaseStatus = false;
    private final SelectedBook selectedBook = BookMainPanel.selectedBook;
    public UpdateBook(BookService bookService1) {
        this.bookService = bookService1;
    }

    @Override
    public void validateBookData(String codeValue, String nameValue, String assortmentValue, String priceValue) {
        super.validateBookData(codeValue, nameValue, assortmentValue, priceValue);

        matchingBook(nameValue, codeValue, priceBigDecimal);
    }

    private void matchingBook(String nameValue, String codeValue,BigDecimal priceValue){
        if(!selectedBook.getBookName().equals(nameValue)){
            if(bookService.bookExistsByCode(codeValue)){
                errors.put("codeDuplicate", "Book with that code already exist");
            }else {
                bookService.updateBookName(nameValue, selectedBook.getBookId());
                dataBaseStatus = true;
            }
        } else if (!selectedBook.getCode().equals(codeValue)) {
            if(bookService.bookExistsByName(nameValue)){
                errors.put("nameDuplicate", "Book with that name already exist");
            }else {
                bookService.updateCode(codeValue, selectedBook.getBookId());
                dataBaseStatus = true;
            }
        }else if (selectedBook.getPrice().compareTo(priceValue) !=0 ) {
                bookService.updatePrice(priceValue, selectedBook.getBookId());
                dataBaseStatus = true;
        }
    }

    @Override
    public boolean getDatabaseStatus() {
        return dataBaseStatus;
    }
}
