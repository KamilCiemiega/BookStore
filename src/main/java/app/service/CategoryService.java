package app.service;

import app.views.UserPanel.Books.Book;
import app.views.UserPanel.Category.CategoryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    public CategoryService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Transactional
    public <T> boolean addCategory(String categoryName, Integer parentId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO category (name, parent_id, creationdate) VALUES (?, ?, CURRENT_DATE)");
            if (parentId == null) {
                preparedStatement.setNull(2, Types.NULL);
            } else {
                preparedStatement.setInt(2, parentId);
            }
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();

            logger.info("Category added" + categoryName);
            return true;
        } catch (SQLException ex) {
            logger.error("Error while adding category to the database", ex);
            return false;
        }
    }

    public List<CategoryData> getAllCategory() {
        List<CategoryData> categoryList = new ArrayList<>();
        String sql = "SELECT category_id, name, parent_id FROM category";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int  categoryId = resultSet.getInt("category_id");
                String categoryName = resultSet.getString("name");
                int parentId = resultSet.getInt("parent_id");
                categoryList.add(new CategoryData(categoryId, categoryName, parentId));
            }
        } catch (SQLException e) {
            logger.error("Failed while trying to connect to the database", e);
        }
        return categoryList;
    }

    public boolean checkIfBookWithCategoryExist(int categoryId) {
        String sql = "SELECT * FROM books WHERE category_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if(count > 0){
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Cant' find book", e);
            return false;
        }
        return false;
    }
    public boolean deleteCategory(int categoryId) {
        String deleteBookQuery = "DELETE FROM category WHERE category_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteBookQuery)) {
            preparedStatement.setInt(1, categoryId);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Book deleted successfully. Rows affected: " + rowsAffected);
                return true;
            } else {
                logger.info("No book found with id: " + categoryId);
                return false;
            }
        } catch (SQLException e){
            logger.error("Failed to delete book", e);
            return false;
        }
    }

    public Map<Integer, Integer> getBooksByCategory() {
        Map<Integer, Integer> booksByCategory = new HashMap<>();
        String sql = "SELECT category_id, COUNT(book_id) AS booksNumber FROM books GROUP BY category_id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int categoryId = resultSet.getInt("category_id");
                int booksNumber = resultSet.getInt("booksNumber");
                booksByCategory.put(categoryId, booksNumber);
            }
        } catch (SQLException e) {
            logger.error("Error while fetching books by category", e);
        }
        return booksByCategory;
    }

    public List<Book> getBooksByCategoryId(int theCategoryId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT book_id, book_name, code, price, last_update, category_id FROM books where category_id = ?";
        final int categoryId = theCategoryId;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, categoryId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("book_id");
                    String bookName = resultSet.getString("book_name");
                    String code = resultSet.getString("code");
                    BigDecimal price = resultSet.getBigDecimal("price");
                    Timestamp lastUpdate = resultSet.getTimestamp("last_update");
                    Integer categoryIdResult = resultSet.getInt("category_id");

                    books.add(new Book(id, bookName, code, price, lastUpdate, categoryIdResult));
                }
            }
        } catch (SQLException e) {
            logger.error("Failed while trying to connect to the database", e);
        }
        return books;
    }
}
