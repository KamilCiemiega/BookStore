package app.service;

import app.views.UserPanel.Books.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    @Autowired
    public BookService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT id, book_name, code, price, last_update FROM books";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String bookName = resultSet.getString("book_name");
                String code = resultSet.getString("code");
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp lastUpdate = resultSet.getTimestamp("last_update");
                books.add(new Book(id, bookName, code, price, lastUpdate));
            }
        } catch (SQLException e) {
            logger.error("Failed while trying to connect to the database");
        }
        return books;
    }
}
