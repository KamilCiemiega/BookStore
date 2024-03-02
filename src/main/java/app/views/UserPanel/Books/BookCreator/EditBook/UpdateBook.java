package app.views.UserPanel.Books.BookCreator.EditBook;


import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.ValidateBook;
import app.views.UserPanel.Books.BookMainPanel;
import app.views.UserPanel.Books.SendBook;

import java.math.BigDecimal;

public class UpdateBook extends ValidateBook implements SendBook {

    protected final BookService bookService;
    private boolean dataBaseStatus = false;
    private final SelectedBook selectedBook = BookMainPanel.selectedBook;
    public UpdateBook(BookService bookService1) {
        this.bookService = bookService1;
    }

    @Override
    public void validateBookData(String codeValue, String nameValue, String assortmentValue, String priceValue) {
        super.validateBookData(codeValue, nameValue, assortmentValue, priceValue);

        matchingBook(nameValue, codeValue);
    }

    private void matchingBook(String nameValue, String codeValue){
        if(!selectedBook.getBookName().equals(nameValue)){
            if(bookService.bookExistsByCode(codeValue)){
                errors.put("codeDuplicate", "Book with that code already exist");
            }
            //dodaj nazwę
        } else if (!selectedBook.getCode().equals(codeValue)) {
            if(bookService.bookExistsByName(nameValue)){
                errors.put("nameDuplicate", "Book with that name already exist");
            }
            //dodaj kod
        }
    }

    //    @Override
//    protected void validateBookData(String codeValue, String nameValue, String assortmentValue, String priceValue) {
//        super.validateBookData(codeValue, nameValue, assortmentValue, priceValue);
////        if(errors.isEmpty()){
////            List<Book> matchingBooks = getAllBooks(codeValue, nameValue, priceBigDecimal);
////            if(matchingBooks.isEmpty()){
////
////            }else {
////
////            }
////        }
////        if(errors.isEmpty() && ){
////            dataBaseStatus = true;
////            sendDataToDatabase(codeValue, nameValue, priceBigDecimal);
////        }
//    }
//
//    private List<Book> getAllBooks(String codeValue, String nameValue, BigDecimal priceValue){
//        List<Book> books = bookService.getAllBooks();
//        List<Book> matchingBooks = new ArrayList<>();
//
//        for (Book book : books) {
//            if (book.getBookName().equals(nameValue) || book.getCode().equals(codeValue) ||
//                    book.getPrice().equals(priceValue)) {
//                matchingBooks.add(book);
//            }
//        }
//
//        return matchingBooks;
//    }
//
//    @Override
//    protected void sendDataToDatabase(String codeValue, String nameValue, BigDecimal priceValue) {
//
//    }
    @Override
    public void sendBookDataToDatabase(String codeValue, String nameValue, BigDecimal priceValue) {

    }
    public boolean getDataBaseStatus() {
        return dataBaseStatus;
    }


}
