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
            String sql = "SELECT first_name, last_name, email, password FROM users";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String firstName = resultSet.getString("first_name");
                        String lastName = resultSet.getString("last_name");
                        String email = resultSet.getString("email");
                        String password = resultSet.getString("password");

                        User user = new User(firstName, lastName, email, password);
                        userList.add(user);
                    }
                }
            }
        } catch (SQLException e) {
            logger.error("Failed to get users from the database", e);
        }

        return userList;
    }
}
