package app.views.UserPanel.Books.BookCreator.EditBook;

import app.service.BookService;

public class getCategoryName {

    private final BookService bookService;
    public getCategoryName(BookService bookService) {
        this.bookService = bookService;
    }

    private String getCategoryNameById(Integer categoryId){
        return  bookService.categoryName(categoryId);
    }
}
