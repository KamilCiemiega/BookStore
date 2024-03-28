package app.views.UserPanel.Books.BookCreator.EditBook;

import app.service.BookService;

public class GetCategoryName {

    private final BookService bookService;
    public GetCategoryName(BookService bookService) {
        this.bookService = bookService;
    }

    public String getCategoryNameById(Integer categoryId){
        return bookService.categoryName(categoryId);
    }
}
