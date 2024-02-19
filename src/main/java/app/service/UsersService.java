package app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.Model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {
    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    public UsersService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        System.out.println(userList);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "select u.id, u.first_name,u.last_name, u.password, u.email_address, r.role_name from users u\n" +
                    "join roles r on r.id = u.role_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer userId = resultSet.getInt("id");
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        String email = resultSet.getString("email_address");
                        String password = resultSet.getString("password");
                        String roleName = resultSet.getString("role_name");

                        User user = new User(userId,firstName, lastName, email, password, roleName);
                        userList.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get users from the database", e);
        }

        return userList;
    }

    public void updateUserData(String name, String surname, String email, String password, Integer roleId, Integer id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, password = ?, email_address = ?, role_id = ?  WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, email);
                preparedStatement.setInt(5, roleId);
                preparedStatement.setInt(6, id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    logger.info("Updated data in the database for ID: {}", id);
                } else {
                    logger.error("No data found to update in the database for ID: {}", id);
                }
            } catch (SQLException e) {
                logger.error("Failed to execute SQL query", e);
            }
        } catch (SQLException e) {
            logger.error("Can't connect to the Database", e);
        }
    }
}
