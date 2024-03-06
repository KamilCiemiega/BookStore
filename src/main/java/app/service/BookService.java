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
        String sql = "SELECT book_id, book_name, code, price, last_update FROM books";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("book_id");
                String bookName = resultSet.getString("book_name");
                String code = resultSet.getString("code");
                BigDecimal price = resultSet.getBigDecimal("price");
                Timestamp lastUpdate = resultSet.getTimestamp("last_update");

                books.add(new Book(id, bookName, code, price, lastUpdate));
            }
        } catch (SQLException e) {
            logger.error("Failed while trying to connect to the database", e);
        }
        return books;
    }
    public boolean bookExistsByCode(String code) {
        String sql = "SELECT COUNT(*) FROM books WHERE code = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, code);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to check if book exists by code",e);
        }
        return false;
    }

    public boolean bookExistsByName(String name) {
        String sql = "SELECT COUNT(*) FROM books WHERE book_name = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to check if book exists by name", e);
        }
        return false;
    }

    public void insertBook(String bookName, String code, BigDecimal price) {
        String sql = "INSERT INTO books (book_name, code, price) VALUES (?, ?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, bookName);
            preparedStatement.setString(2, code);
            preparedStatement.setBigDecimal(3, price);

            int rowsAffected = preparedStatement.executeUpdate();

            logger.info("New book added to the database. Rows affected: {}", rowsAffected);
            logger.info("New book added to the database");
        } catch (SQLException e) {
            logger.error("Failed to insert book into database", e);
        }
    }

    public void updateBookName(String newBookName, Integer bookId) {
        String updateBookNameQuery = "UPDATE books SET book_name = ? WHERE book_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateBookNameQuery)) {
            preparedStatement.setString(1, newBookName);
            preparedStatement.setInt(2, bookId);

            int rowsAffected = preparedStatement.executeUpdate();

            logger.info("Updated book_name in the database. Rows affected: {}", rowsAffected);
        } catch (SQLException e) {
            logger.error("Failed to update book_name in the database", e);
        }
    }

    public void updateCode(String newCode,Integer bookId) {
        String updateCodeQuery = "UPDATE books SET code = ? WHERE book_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateCodeQuery)) {
            preparedStatement.setString(1, newCode);
            preparedStatement.setInt(2, bookId);

            int rowsAffected = preparedStatement.executeUpdate();

            logger.info("Updated code in the database. Rows affected: {}", rowsAffected);
        } catch (SQLException e) {
            logger.error("Failed to update code in the database", e);
        }
    }
    public void updatePrice(BigDecimal newPrice,Integer bookId) {
        String updateCodeQuery = "UPDATE books SET price = ? WHERE book_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateCodeQuery)) {
            preparedStatement.setBigDecimal(1, newPrice);
            preparedStatement.setInt(2, bookId);

            int rowsAffected = preparedStatement.executeUpdate();

            logger.info("Updated code in the database. Rows affected: {}", rowsAffected);
        } catch (SQLException e) {
            logger.error("Failed to update code in the database", e);
        }
    }

    public boolean deleteBook(int bookId) {
        String deleteBookQuery = "DELETE FROM books WHERE book_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteBookQuery)) {
            preparedStatement.setInt(1, bookId);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                logger.info("Book deleted successfully. Rows affected: " + rowsAffected);
                return true;
            } else {
                logger.info("No book found with id: " + bookId);
                return false;
            }
        } catch (SQLException e) {
            logger.error("Failed to delete book", e);
            return false;
        }
    }

}
