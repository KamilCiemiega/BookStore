package app.service;

import app.views.UserPanel.Category.CategoryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
}
