package com.epolsoft.brest.config;

import com.epolsoft.brest.dao.api.FileNameDao;
import com.epolsoft.brest.dao.api.PersonDao;
import com.epolsoft.brest.dao.impl.FileNameDaoImpl;
import com.epolsoft.brest.dao.impl.PersonDaoImpl;
import com.epolsoft.brest.file.impl.CSVFileOfPersonReader;
import com.epolsoft.brest.file.api.FileOfPersonReader;
import com.epolsoft.brest.service.impl.DbResaveService;
import com.epolsoft.brest.service.api.ResaveService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@PropertySource("jdbc.properties")
public class AppConfig {

    @Value("${jdbc.driverClassName}")
    private String DB_DRIVER;
    @Value("${jdbc.url}")
    private String DB_URL;
    @Value("${jdbc.username}")
    private String DB_USERNAME;
    @Value("${jdbc.password}")
    private String DB_PASSWORD;

    @Bean
    public ResaveService resaveService() {
        return new DbResaveService(personDao(), fileOfPersonReader(), fileNameDao());
    }

    @Bean
    public PersonDao personDao() {
        return new PersonDaoImpl(jdbcTemplate());
    }

    @Bean
    public FileNameDao fileNameDao() {
        return new FileNameDaoImpl(jdbcTemplate());
    }

    @Bean
    public FileOfPersonReader fileOfPersonReader() {
        return new CSVFileOfPersonReader();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);

        ResourceDatabasePopulator tables = new ResourceDatabasePopulator();
        tables.addScript(new ClassPathResource("/start_schema.sql"));
        DatabasePopulatorUtils.execute(tables, dataSource);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
