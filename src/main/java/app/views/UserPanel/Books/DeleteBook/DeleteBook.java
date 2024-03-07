package app.views.UserPanel.Books.DeleteBook;

import app.service.BookService;
import app.views.UserPanel.Books.BookCreator.EditBook.SelectedBook.GetSelectedBookValue;

import java.util.Set;


public class DeleteBook {

    private final BookService bookService;

    public DeleteBook(BookService bookService) {
        this.bookService =bookService;
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
            } else {
                deleteBookStatus = false;

            }
            return  deleteBookStatus;
        }
    }
