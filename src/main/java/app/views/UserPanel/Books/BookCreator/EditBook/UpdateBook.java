package app.views.UserPanel.Books.BookCreator.EditBook;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook.GetSelectedBookValue;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook.SelectedBook;
import app.views.UserPanel.Books.BookCreator.SendBookStatus;
import app.views.UserPanel.Books.BookCreator.ValidateBook;

import java.math.BigDecimal;

public class UpdateBook extends ValidateBook implements SendBookStatus {

    protected final BookService bookService;
    private boolean dataBaseStatus = false;
    private boolean noChanges = false;
    private final GetCategoryName getCategoryName;
    private final SelectedBook selectedBook = GetSelectedBookValue.selectedBook;
    public UpdateBook(BookService bookService) {
        this.bookService = bookService;
        this.getCategoryName = new GetCategoryName(bookService);
    }

    @Override
    public void validateBookData(String codeValue, String nameValue, String categoryValue, String priceValue, Integer categoryId) {
        super.validateBookData(codeValue, nameValue, categoryValue, priceValue, categoryId);

        matchingBook(nameValue, codeValue, priceBigDecimal, categoryId);
    }

    private void matchingBook(String nameValue, String codeValue,BigDecimal priceValue, Integer categoryId){
        boolean nameChanged = !selectedBook.getBookName().equals(nameValue);
        boolean codeChanged = !selectedBook.getCode().equals(codeValue);
        boolean categoryChanged = !categoryId.equals(selectedBook.getCategoryId());
        boolean priceChanged = selectedBook.getPrice().compareTo(priceValue) != 0;

            if (!nameChanged && !codeChanged && !priceChanged && !categoryChanged){
                noChanges = true;
            }
          if (nameChanged) {
              if (bookService.bookExistsByName(nameValue)) {
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
          if (categoryChanged){
              bookService.updateCategory(categoryId ,selectedBook.getBookId());
              dataBaseStatus = true;

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
