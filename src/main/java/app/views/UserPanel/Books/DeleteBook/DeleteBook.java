package app.views.UserPanel.Books.DeleteBook;

import app.service.BookService;
import app.views.UserPanel.Books.Book;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook.GetSelectedBookValue;

import java.util.List;
import java.util.Set;


public class DeleteBook {

    private final BookService bookService;
    private final BookGridRefresher gridRefresher;

    public DeleteBook(BookService bookService,BookGridRefresher gridRefresher) {
        this.bookService =bookService;
        this.gridRefresher = gridRefresher;
    }

        public boolean deleteBookFromDatabase() {
            Set<Integer> selectedBooksIds = GetSelectedBookValue.getSelectedBookIds();
            boolean deleteBookStatus = true;
            if (!selectedBooksIds.isEmpty()) {
                for (Integer bookId : selectedBooksIds) {
                    boolean deleteBookService = bookService.deleteBook(bookId);
                    if (!deleteBookService) {
                        deleteBookStatus = false;
                        break;
                    }
                }
                if (deleteBookStatus) {
                    List<Book> updatedBooks = bookService.getAllBooks();
                    gridRefresher.refreshGrid(updatedBooks);
                }
            } else {
                deleteBookStatus = false;

            }
            return  deleteBookStatus;
        }
    }
