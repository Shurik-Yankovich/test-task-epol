package com.epolsoft.brest.config;

import com.epolsoft.brest.dao.api.FileNameDao;
import com.epolsoft.brest.dao.api.PersonDao;
import com.epolsoft.brest.dao.impl.FileNameDaoImpl;
import com.epolsoft.brest.dao.impl.PersonDaoImpl;
import com.epolsoft.brest.file.CSVFileOfPersonReader;
import com.epolsoft.brest.file.FileOfPersonReader;
import com.epolsoft.brest.service.DbResaveService;
import com.epolsoft.brest.service.ResaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
//@PropertySource("classpath:application.properties")
public class AppConfig {

    private final String DB_DRIVER = "org.postgresql.Driver";
    private final String DB_URL = "jdbc:postgresql://localhost:5432/resavedb";
    private final String DB_USERNAME = "postgres";
    private final String DB_PASSWORD = "admin";

//    @Bean
//    @Scope(value = "prototype")
//    public Logger logger()
//    {
//        return LoggerFactory.getLogger(DbResaveService.class);
//    }

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

//        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
//        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
//        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.user")));
//        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.pass")));

//        Resource initSchema = new ClassPathResource("./schema.sql");
//        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
//        DatabasePopulatorUtils.execute(databasePopulator, dataSource);

        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
