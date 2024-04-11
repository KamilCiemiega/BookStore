package app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import app.views.UserPanel.Users.User;

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
            String sql = "select u.user_id, u.first_name,u.last_name, u.password, u.email_address, r.role_name from users u\n" +
                    "join roles r on r.role_id = u.role_id";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Integer userId = resultSet.getInt("user_id");
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

    public void updateUserData(String name, String surname, String email, String password, Integer roleId, Integer userId) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, password = ?, email_address = ?, role_id = ?  WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, hashedPassword);
                preparedStatement.setString(4, email);
                preparedStatement.setInt(5, roleId);
                preparedStatement.setInt(6, userId);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    logger.info("Updated data in the database for ID: {}", userId);
                } else {
                    logger.error("No data found to update in the database for ID: {}", userId);
                }
            } catch (SQLException e) {
                logger.error("Failed to execute SQL query", e);
            }
        } catch (SQLException e) {
            logger.error("Can't connect to the Database", e);
        }
    }
    public void deleteUser(Integer userId) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "DELETE FROM users WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, userId);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Deleted user with ID: {}", userId);
                } else {
                    logger.error("Can't delete user from database with ID: {}", userId);
                }
            } catch (SQLException e) {
                logger.error("Failed to execute SQL query", e);
            }
        } catch (SQLException e) {
            logger.error("Can't connect to the Database", e);
        }
    }
    public void insertNewUser(String firstName, String lastName, String password, String emailAddress, int roleId) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, password, email_address, role_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, hashedPassword);
                preparedStatement.setString(4, emailAddress);
                preparedStatement.setInt(5, roleId);

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    logger.info("A new user was inserted successfully!");
                }
            } catch (SQLException e) {
                logger.error("Failed to execute SQL query", e);
            }
        } catch (SQLException e) {
            logger.error("Can't connect to the Database", e);
        }
    }
}
