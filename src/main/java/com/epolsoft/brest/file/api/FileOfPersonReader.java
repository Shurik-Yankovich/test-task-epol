package com.epolsoft.brest.file.api;

import com.epolsoft.brest.exception.ReadFormatNotSupportedException;
import com.epolsoft.brest.model.Person;

import java.util.List;

public interface FileOfPersonReader {

    List<Person> readData(String filePath) throws ReadFormatNotSupportedException;
}
