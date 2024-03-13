package app.service;

import app.views.UserPanel.Category.CategoryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public <T> boolean addCategory(String categoryName, T parentId) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO category (name, parent_id, creationdate) VALUES (?, ?, CURRENT_DATE)");
            if (parentId == null) {
                preparedStatement.setObject(2, null);
            } else {
                preparedStatement.setObject(2, parentId);
            }
            preparedStatement.setString(1, categoryName);
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
}
