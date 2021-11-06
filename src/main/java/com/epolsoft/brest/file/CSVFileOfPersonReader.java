package com.epolsoft.brest.file;

import com.epolsoft.brest.exception.ReadFormatNotSupportedException;
import com.epolsoft.brest.model.Person;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVFileOfPersonReader implements FileOfPersonReader {

    private final int NUMBER_OF_COLUMNS = 4;
    private final String FILE_FORMAT = ".csv";

    @Override
    public List<Person> readData(String filePath) throws ReadFormatNotSupportedException {
        List<Person> resultList = new ArrayList<>();
        try (FileReader filereader = new FileReader(filePath + FILE_FORMAT);
             CSVReader csvReader = new CSVReader(filereader)) {
            String[] values;
//            CsvToBean csvToBean = new CsvToBean();
            values = csvReader.readNext();
            if (values == null || values.length != NUMBER_OF_COLUMNS) {
                throw new ReadFormatNotSupportedException(filePath + ".CSV неверный формат, данные не записываются");
            }
            while ((values = csvReader.readNext()) != null) {
                resultList.add(new Person(0,
                        values[0].toUpperCase(),
                        values[1].toUpperCase(),
                        values[2].toUpperCase(),
                        values[3].toUpperCase()));
                System.out.println();
            }
        } catch (CsvValidationException | IOException e) {
            throw new ReadFormatNotSupportedException(filePath + ".CSV невозможно прочитать");
        }
        return resultList;
    }
}
