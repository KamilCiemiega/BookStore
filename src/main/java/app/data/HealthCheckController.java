package app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/health")
    public String checkHealth() {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            return "Connection to the database is successful!";
        } catch (Exception e) {
            return "Error: Unable to connect to the database.";
        }
    }
}