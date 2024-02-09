package app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class AccountSettingsService {
    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    public AccountSettingsService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean addAccountImagePath(String fileName){
        try (Connection connection = dataSource.getConnection()){
            String sql = "INSERT INTO file_path (file_name) VALUES (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1, fileName);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    logger.info("Inserted data into the database:File Name{}.", fileName);
                    return true;
                }else {
                    logger.error("Failed to insert data into the database: No rows affected.");
                    return false;
                }
            }catch (SQLException e){
                logger.error("Can't insert data to Database", e);
                return false;
            }

        }catch (SQLException e){
            logger.error("Can't connect to the Database", e);
            return false;
        }
    }
}
