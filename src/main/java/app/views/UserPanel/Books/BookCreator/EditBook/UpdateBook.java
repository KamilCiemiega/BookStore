package app.views.UserPanel.Books.BookCreator.EditBook;


import app.service.BookService;
import app.views.UserPanel.Books.Book;
import app.views.UserPanel.Books.BookCreator.AddBook.SendBookToTheDatabase;
import app.views.UserPanel.Books.BookCreator.ValidateBook;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UpdateBook extends SendBookToTheDatabase {

    public UpdateBook(BookService bookService) {
        super(bookService);

    }

    @Override
    public void validateBookData(String codeValue, String nameValue, String assortmentValue, String priceValue) {
        super.validateBookData(codeValue, nameValue, assortmentValue, priceValue);
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
}
