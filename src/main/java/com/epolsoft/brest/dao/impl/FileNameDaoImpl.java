package com.epolsoft.brest.dao.impl;

import com.epolsoft.brest.dao.api.FileNameDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileNameDaoImpl implements FileNameDao {

    private JdbcTemplate jdbcTemplate;

    public FileNameDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String read(Integer id) {
        return jdbcTemplate.queryForObject("SELECT file_path FROM file_name WHERE file_name_id=1", String.class);
    }
}
