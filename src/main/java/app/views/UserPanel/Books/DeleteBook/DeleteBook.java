package app.views.UserPanel.Books.DeleteBook;

import app.service.BookService;

import java.util.HashSet;
import java.util.Set;


public class DeleteBook {

    private final BookService bookService;
    private final Set<Integer> selectedBooks = new HashSet<>();


    public DeleteBook(BookService bookService) {
        this.bookService =bookService;
    }
    public void addBookToList(Integer bookId){
        selectedBooks.add(bookId);
    }

    public boolean deleteBook(){
        boolean deleteBookStatus = true;

        if (!selectedBooks.isEmpty()){
            for (Integer bookId : selectedBooks){
                boolean deleteBookService = bookService.deleteBook(bookId);
                if (!deleteBookService){
                    deleteBookStatus = false;
                    break;
                }
            }
        } else {
            deleteBookStatus = false;
        }
        return deleteBookStatus;
    }

}
