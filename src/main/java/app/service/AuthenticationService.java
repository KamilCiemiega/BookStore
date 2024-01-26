package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AuthenticationService  {

    private final DataSource dataSource;

    @Autowired
    public AuthenticationService (DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean insertData(String firstName, String lastName, String password, String email) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "INSERT INTO users (first_name, last_name, password, email_address) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, password);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}