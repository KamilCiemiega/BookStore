package app.views.UserPanel.Books.BookCreator.EditBook;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook.GetSelectedBookValue;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook.SelectedBook;
import app.views.UserPanel.Books.BookCreator.SendBookStatus;
import app.views.UserPanel.Books.BookCreator.ValidateBook;
import app.views.UserPanel.Books.BookMainPanel;


import java.math.BigDecimal;

public class UpdateBook extends ValidateBook implements SendBookStatus {

    protected final BookService bookService;
    private boolean dataBaseStatus = false;

    private boolean noChanges = false;
    private final SelectedBook selectedBook = GetSelectedBookValue.selectedBook;
    public UpdateBook(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void validateBookData(String codeValue, String nameValue, String assortmentValue, String priceValue) {
        super.validateBookData(codeValue, nameValue, assortmentValue, priceValue);

        matchingBook(nameValue, codeValue, priceBigDecimal);
    }

    private void matchingBook(String nameValue, String codeValue,BigDecimal priceValue){
        boolean nameChanged = !selectedBook.getBookName().equals(nameValue);
        boolean codeChanged = !selectedBook.getCode().equals(codeValue);
        boolean priceChanged = selectedBook.getPrice().compareTo(priceValue) != 0;

            if (!nameChanged && !codeChanged && !priceChanged){
                noChanges = true;
            }
          if (nameChanged) {
              if (bookService.bookExistsByName(nameValue)) {
                  System.out.println(nameValue);
                  errors.put("nameDuplicate", "Book with that name already exists");
              } else {
                  bookService.updateBookName(nameValue, selectedBook.getBookId());
                  dataBaseStatus = true;
              }
          }
          if (codeChanged) {
                if (bookService.bookExistsByCode(codeValue)) {
                    errors.put("codeDuplicate", "Book with that code already exists");
                } else {
                    bookService.updateCode(codeValue, selectedBook.getBookId());
                    dataBaseStatus = true;
                }
          }
          if (priceChanged) {
                bookService.updatePrice(priceValue, selectedBook.getBookId());
                dataBaseStatus = true;
          }

    }

    @Override
    public boolean getDatabaseStatus() {
        return dataBaseStatus;
    }

    public boolean isNoChanges() {
        return noChanges;
    }
}
