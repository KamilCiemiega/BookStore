package app.views.UserPanel.Category;

import app.service.BookService;
import app.views.UserPanel.Books.Book;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;

import java.util.List;

public class BookListByCategory {
    private static Grid<Book> grid;
    private static List<Book> allBooksFromService;
    public BookListByCategory(Grid<Book> grid, BookService bookService) {
        this.grid = grid;
        allBooksFromService = bookService.getAllBooks();
    }

    public static void updateBooksByCategory(List<Book> booksInCategory) {
            grid.setItems(booksInCategory);
    }

    public static void allCategoryListener(H2 allCategoryElement){
        allCategoryElement.addClickListener(e -> {
           grid.setItems(allBooksFromService);
        });
    }
}
