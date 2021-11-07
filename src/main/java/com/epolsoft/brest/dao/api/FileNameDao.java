package com.epolsoft.brest.dao.api;

import com.epolsoft.brest.exception.DataNotFoundException;

public interface FileNameDao {
    String read(Integer id) throws DataNotFoundException;
}
