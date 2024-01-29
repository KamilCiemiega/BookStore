package app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class AuthenticationService {

    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    public AuthenticationService (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertData(String firstName, String lastName, String password, String email) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, password, email_address) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, password);
                preparedStatement.setString(4, email);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    logger.info("Inserted data into the database: First Name={}, Last Name={}, Email={}", firstName, lastName, email);
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error("Can't insert data to Database", e);
            return false;
        }
        return false;
    }
    public boolean authenticateUser(String email, String password) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT password FROM users WHERE email_address = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedPassword = resultSet.getString("password");
                        if(password.equals(storedPassword)){
                            logger.info("Authentication successful for user with email: {}", email);
                            return true;
                        }else {
                            logger.warn("Authentication failed for user with email: {}. Incorrect password.", email);
                        }

                    }else {
                        logger.warn("No user found in the database with email: {}", email);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Error during user authentication", e);

        }
        return false;
    }
}