package app.service;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.server.SessionExpiredException;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public AuthenticationService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertData(String firstName, String lastName, String password, String email) {
        try (Connection connection = dataSource.getConnection()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(password);

            String sql = "INSERT INTO users (first_name, last_name, password, email_address) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, hashedPassword);
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
            String sql = "SELECT id, first_name, last_name, password, email_address FROM users WHERE email_address = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedPassword = resultSet.getString("password");
                        if (password.equals(storedPassword)) {
                            String firstName = resultSet.getString("first_name");
                            String lastName = resultSet.getString("last_name");
                            Integer userID = resultSet.getInt("id");
                            try {
                                VaadinRequest currentRequest = VaadinRequest.getCurrent();
                                VaadinSession vaadinSession = currentRequest.getService().findVaadinSession(currentRequest);
                                String sessionId = vaadinSession.getSession().getId();
                                UserContext.setFirstName(firstName);
                                UserContext.setLastName(lastName);
                                UserContext.setEmail(email);
                                UserContext.setUserID(userID);
                                UserContext.setPassword(password);
                                UserContext.setSesionId(sessionId);
                                return true;
                            } catch (SessionExpiredException e) {
                                Notification.show("Your session has expired because you have been inactive for 30 minutes. Please log in again.", 3000, Notification.Position.TOP_CENTER);
                                UI.getCurrent().navigate("");
                                logger.info("Session expired for user with Email: {}", email);
                            }
                        } else {
                            logger.warn("Authentication failed for user with email: {}. Incorrect password.", email);
                        }
                    } else {
                        logger.warn("No user found in the database with email: {}", email);
                    }
                }
            }
        } catch (SQLException e) {
            Notification.show("Error during user authentication. Email: {}" + email);
            logger.error("Error during user authentication. Email: {}", email, e);
        }
        return false;
    }
    public Long getUserIdByEmail(String email) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "SELECT id FROM users WHERE email_address = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getLong("id");
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Can't retrieve user ID from the database", e);
        }
        return null;
    }
}
