package com.epolsoft.brest.service;

import com.epolsoft.brest.dao.api.FileNameDao;
import com.epolsoft.brest.dao.api.PersonDao;
import com.epolsoft.brest.exception.ReadFormatNotSupportedException;
import com.epolsoft.brest.file.FileOfPersonReader;
import com.epolsoft.brest.model.CSVFile;
import com.epolsoft.brest.model.Person;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Date;
import java.util.List;

@Service
public class DbResaveService implements ResaveService {

    private final PersonDao personDao;
    private final FileOfPersonReader fileOfPersonReader;
    private final FileNameDao fileNameDao;
    private CSVFile csvFile;
    boolean isNeededToSave;
    private static final Logger logger = Logger.getLogger(DbResaveService.class);

    public DbResaveService(PersonDao personDao, FileOfPersonReader fileOfPersonReader, FileNameDao fileNameDao) {
        this.personDao = personDao;
        this.fileOfPersonReader = fileOfPersonReader;
        this.fileNameDao = fileNameDao;
        this.isNeededToSave = false;
    }

    @Override
    @Scheduled(cron = "0/60 * * * * ?")
    public void service() {
        if (isNeededToSave) {
            try {
                List<Person> people = fileOfPersonReader.readData(csvFile.getFilename());
                for (Person person : people) {
                    personDao.save(person);
                }
                logger.info(csvFile.getFilename() + ".CSV прочитан и сохранен");
            } catch (ReadFormatNotSupportedException e) {
                logger.info(e.getMessage());
            }
        } else {
            logger.info(csvFile.getFilename() + ".CSV был прочитан ранее, нет новых данных");
        }
    }

    @Override
    @Scheduled(cron = "0/30 * * * * ?")
    public void readFileName() {
        String filename = fileNameDao.read(1);
        File file = new File(filename + ".csv");
        Date lastModified = new Date(file.lastModified());
        if (csvFile == null || !csvFile.getFilename().equals(filename)) {
            csvFile = new CSVFile(filename, lastModified);
            isNeededToSave = true;
        } else {
            isNeededToSave = !csvFile.getLastModified().equals(lastModified);
        }
    }
}
