package app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

public class CategoryService {
    private final DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(DataSource dataSource) {
        this.dataSource = dataSource;
    }


}
