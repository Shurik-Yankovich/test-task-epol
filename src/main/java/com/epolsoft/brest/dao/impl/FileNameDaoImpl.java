package com.epolsoft.brest.dao.impl;

import com.epolsoft.brest.dao.api.FileNameDao;
import com.epolsoft.brest.exception.DataNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileNameDaoImpl implements FileNameDao {

    private JdbcTemplate jdbcTemplate;

    public FileNameDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String read(Integer id) throws DataNotFoundException {
        String fileName;
        try {
            fileName = jdbcTemplate.queryForObject("SELECT file_path FROM file_name WHERE id=1", String.class);
            return fileName;
        } catch (Exception e) {
            throw new DataNotFoundException("не удалось прочитать путь к CSV файлу");
        }
    }
}
