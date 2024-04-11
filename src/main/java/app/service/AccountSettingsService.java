package app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AccountSettingsService {
    private final DataSource dataSource;
    @Autowired
    private AuthenticationService authenticationService;
    private static final Logger logger = LoggerFactory.getLogger(AccountSettingsService.class);

    @Autowired
    public AccountSettingsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addAccountImage(String fileName) {
        String userEmail = UserContext.getEmail();
        Long userId = authenticationService.getUserIdByEmail(userEmail);

        if(userId != null) {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "INSERT INTO profile_picture (file_name, user_id) VALUES (?, ? )";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, fileName);
                    preparedStatement.setLong(2, userId);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        logger.info("Inserted data into the database:File Name{}.", fileName);
                    } else {
                        logger.error("Failed to insert data into the database: No rows affected.");
                    }
                } catch (SQLException e) {
                    logger.error("Can't insert data to Database", e);
                }

            } catch (SQLException e) {
                logger.error("Can't connect to the Database", e);
            }
        }
    }

        public String getAccountImageName(Integer userId){
            String fileName = null;
            try (Connection connection = dataSource.getConnection()) {
                String sql = "SELECT file_name FROM profile_picture WHERE user_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setInt(1, userId);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            fileName = resultSet.getString("file_name");
                        }
                    }
                }
            } catch (SQLException e) {
                logger.error("Failed to retrieve account image name for user ID: " + userId, e);
            }
            return fileName;
        }

    public boolean deleteAccountImage(String fileName) {
        String userEmail = UserContext.getEmail();
        Long userId = authenticationService.getUserIdByEmail(userEmail);

        if (userId != null) {
            try (Connection connection = dataSource.getConnection()) {
                String sql = "DELETE FROM profile_picture WHERE file_name = ? AND user_id = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, fileName);
                    preparedStatement.setLong(2, userId);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        logger.info("Deleted data from the database: File Name{}.", fileName);
                        return true;
                    } else {
                        logger.error("No data found to delete from the database for File Name: {}", fileName);
                    }
                } catch (SQLException e) {
                    logger.error("Error while deleting data from the database", e);
                }
            } catch (SQLException e) {
                logger.error("Unable to connect to the database", e);
            }
            return false;
        } else {
            return false;
        }
    }

    public void updateUserData(String name, String surname, String email, Integer user_id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "UPDATE users SET first_name = ?, last_name = ?, email_address = ? WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, email);
                preparedStatement.setInt(4, user_id);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    logger.info("Updated data in the database for ID: {}", user_id);
                } else {
                    logger.error("No data found to update in the database for ID: {}", user_id);
                }
            } catch (SQLException e) {
                logger.error("Failed to execute SQL query", e);
            }
        } catch (SQLException e) {
            logger.error("Can't connect to the Database", e);
        }
    }
    public void updateUserPassword(String password, Integer userId) {

        try (Connection connection = dataSource.getConnection()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(password);
            String sql = "UPDATE users SET password = ? WHERE user_id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, hashedPassword);
                preparedStatement.setInt(2, userId);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    logger.info("Updated password in the database for ID: {}", userId);
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
}
