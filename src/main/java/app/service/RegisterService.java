package app.service;

//import app.data.DatabaseConnection;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class RegisterService {

    public static void insertData(String emailAddress, String firstName, String lastName, String password) {
//        try (Connection connection = DatabaseConnection.connect()) {
//            String query = "INSERT INTO users (emailAddress,firstName, lastName, password) VALUES (?, ?, ?, ?)";
//
//            try (PreparedStatement statement = connection.prepareStatement(query)) {
//                statement.setString(1, emailAddress);
//                statement.setString(2, firstName);
//                statement.setString(3, lastName);
//                statement.setString(4, password);
//
//                statement.executeUpdate();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}